package com.ppfuns.report.controller;

import com.ppfuns.report.entity.CollectBean;
import com.ppfuns.report.utils.UserAnaysisUtils;
import com.ppfuns.util.PpfunsConfigBean;
import com.ppfuns.util.ResultData;
import com.ppfuns.util.ShellCommandUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2020/12/18.
 */
@RestController
@RequestMapping("/anaysis/user/action")
public class SingleUserAnaysisController {

    @Autowired
    private PpfunsConfigBean ppfunsConfigBean;
    private String isRemote;

    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public ModelAndView toPage() {
        ModelAndView mav = new ModelAndView("/report/product/user_action_anaysis");
        return mav;
    }

    @RequestMapping("/get")
    public ResultData getAll(String path, String userName, String uniqueSelect) {
        ResultData resultData = new ResultData();
        StringBuilder cmd = new StringBuilder("hadoop fs -cat ");
        String path2=path.indexOf("/")==0?path:ppfunsConfigBean.getHdfsPath().concat("/").concat(path);
        cmd.append(path2).append("| grep -E ").append(userName);
        List<String> lines = null;
        if (ppfunsConfigBean.getRemote()) {
            lines = ShellCommandUtils.getResultByRemoteExec(ppfunsConfigBean.getRemoteIp(), ppfunsConfigBean.getRemotePort(), ppfunsConfigBean.getRemoteUser(), ppfunsConfigBean.getRemotePassword(), cmd.toString());
        } else {
            lines = localRunScript(cmd.toString());
        }
        if (CollectionUtils.isEmpty(lines)) {
            resultData.setMsg("无数据");
            return resultData;
        }
        List<CollectBean> collectBeans = UserAnaysisUtils.getUserAnaysisList(lines,uniqueSelect);
//        List<SpmNode> collect = collectBeans.stream().map(c -> validateUserAction(c)).collect(Collectors.toList());
        //遍历元素是否存在
        if(!CollectionUtils.isEmpty(lines) && CollectionUtils.isEmpty(collectBeans)){
            resultData.setCode("0");
            resultData.setMsg(StringUtil.join(lines.toArray(),"<br>"));
            return resultData;
        }
        resultData.setResult(collectBeans);
        return resultData;
    }

    private List<String> localRunScript(String cmd) {
        List<String> lines = null;
        Process proc = null;
        BufferedReader in = null;
        try {
            proc = Runtime.getRuntime().exec(cmd);
            in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            lines = in.lines().collect(Collectors.toList());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return lines;
        }

    }



    public SpmNode validateUserAction(CollectBean bean) {
        /* 验证spm是否是可连续直线 */
        int index = 1;
        SpmNode header = new SpmNode(null);
        for (String t : bean.getTimeSortList()) { //给排序好的数据加序号
            for (Map<String, String> map : bean.getSortTimeMap().get(t)) {
                if (map.containsKey("nowSpm") && map.containsKey("afterSpm")) {
                    SpmNode currentNode = new SpmNode(map);currentNode.id=index;
                   if(!findPreNode(header, currentNode)){ //未找到前置节点,把currentNode前置
                       header.last.add(currentNode);
                       currentNode.setPre(header);
                   }
                }
                map.put("id", index++ + "");
            }
        }
        //验证树状spm是否正常
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        print(header);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        return header;
    }
    private void print(SpmNode node){
        if(node.data != null){
            System.out.println("位置："+node.id+"; "+node.data);
        }
        node.last.forEach(s->print(s));

    }
    /**
     * @param pre
     * @param currentNode
     * @return false 未找到前置节点 true已找到前置节点
     */
    public boolean findPreNode(SpmNode pre, SpmNode currentNode) {
        String cur_nowSpm = currentNode.data.get("nowSpm");
        String cur_afterSpm = currentNode.data.get("afterSpm");
        String[] curSpms = cur_afterSpm.split("\\.");
        if (curSpms.length < 5 || curSpms[4].equals("0")) {
            return false;
        }
        if (pre.data != null){
            String pre_nowSmp = pre.data.get("nowSpm");
            String pre_afterSpm = pre.data.get("afterSpm");
            //pre_nowSpm==cur_afterSpm
            String[] preSpms = pre_nowSmp.split("\\.");
            if (curSpms[4].equals(preSpms[4])) { //找到前置节点
                currentNode.setPre(pre);   //添加前置节点
                pre.last.add(currentNode);//添加后置节点
                return true;
            }
        }
        //如果前置节点找不到数据，查看前置节点是否有后置节点
        List<SpmNode> lastNodes = pre.last;
        for (SpmNode s : lastNodes) {
            if (findPreNode(s, currentNode)) {
                return true;
            }
        }
        return false;
    }

    class SpmNode {
        private int id;
        private SpmNode pre;
        private List<SpmNode> last = new ArrayList<>();
        private Map<String, String> data;

        public SpmNode(Map data) {
            this.data = data;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public SpmNode getPre() {
            return pre;
        }

        public void setPre(SpmNode pre) {
            this.pre = pre;
        }



        public List<SpmNode>  getLast() {
            return last;
        }

        public void setLast(List<SpmNode>  last) {
            this.last = last;
        }

        public Map<String, String> getData() {
            return data;
        }

        public void setData(Map<String, String> data) {
            this.data = data;
        }
    }
}
