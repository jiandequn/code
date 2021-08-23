package com.ppfuns;

import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        String pattern = "^.eventsType=(.*);eventsName=(.*);area=(.*);mac=(.*);sn=(.*);userId=(.*);userType=([VODvod|ott|OTT]{1,3});(.*);parentColumnId=(.*);(.*);createTime=(.*):END";
       // String pattern = "^.*eventsType=(.*);mac=(.*);sn=(.*);userId=(.*);userType=([VODvod12]{1,3});(.*);createTime=(.*):END";
        Pattern inputPattern = Pattern.compile(pattern, Pattern.DOTALL
                + (false ? Pattern.CASE_INSENSITIVE : 0));
        String test = "sdfs eventsType=COLUMN_TYPE_TAB;eventsName=页签选项卡;area=0;mac=mac;sn=sn;userId=uid;ca=null;userType=ott;parentColumnId=13187;columnId=13233;nowSpm=13187.PAGE_SELECTED.13233.0.1587460762364;afterSpm=13187.PAGE_SELECTED.13233.0.1587460682524;columnCodeName=精选Tab;pos=PAGE_SELECTED;posName=精选Tab;createTime=2020-04-21 17:19:22\n" +
                "eventsType=COLUMN_TYPE_WHEEL_BIT;";
        String text = "2019-11-01 00:59:53.665 INFO [resin-port-9003-55759][CommonAuthService.java:162] - Collection events:eventsType=auth_product;mac=208B3774BB30;sn=12022160605760208B3774BB30;userId=12050066;userType=VOD;contentId=13619;contentType=1;parentColumnId=6029;code=S100000;message=鉴权通过;operateType=auth_product;createTime=2019-11-01 00:59:53:END";
        Matcher m = inputPattern.matcher(test);
        System.out.println(" dsfsdf="+m.groupCount()+";sdfsd"+m.matches());
        for(int i=0;i<m.groupCount();i++){
            System.out.println(m.group(i+1));
        }

    }

    @Test
    public void shouldAnswerWithTrue1()
    {
//        String pattern = "^- Collection events:eventsType=(.*);(.*)=(.*);createTime=(.*):END";
//        Pattern inputPattern = Pattern.compile(pattern, Pattern.DOTALL
//                + (false ? Pattern.CASE_INSENSITIVE : 0));
//        String text = "2019-11-01 00:59:53.665 INFO [resin-port-9003-55759][CommonAuthService.java:162] - Collection events:eventsType=auth_product;mac=208B3774BB30;sn=12022160605760208B3774BB30;userId=12050066;userType=VOD;contentId=13619;contentType=1;parentColumnId=6029;code=S100000;message=鉴权通过;operateType=auth_product;createTime=2019-11-01 00:59:53:END";
//        Matcher m = inputPattern.matcher(text);
//        System.out.println(" dsfsdf="+m.groupCount()+";sdfsd="+m.matches());
//        for(int i=0;i<m.groupCount();i++){
//            System.out.println(m.group(i+1));
//        }

    }
    @Test
    public void shouldAnswerWithTrue2()
    {
//        String pattern = "(productId|contentId)=(.*);(isEffective|contentType)=([0-9]*);(resourceId|parentColumnId)=(.*)";
        String pattern = "(contentId=([0-9]*);contentType=([0-9]*);parentColumnId=([0-9]*);code=(.*);message=(.*);operateType=(.*))|" +
                "(productId=(.*);isEffective=(.*);resourceId=(.*);operateType=(.*))";
        Pattern inputPattern = Pattern.compile(pattern, Pattern.DOTALL
                + (false ? Pattern.CASE_INSENSITIVE : 0));
        String  text = null;
        text = "contentId=17547;contentType=1;parentColumnId=6029;code=S100000;message=鉴权通过;operateType=auth_product";

        Matcher m = inputPattern.matcher(text);
        System.out.println(" one="+m.groupCount()+";one="+m.matches());
        for(int i=0;i<m.groupCount();i++){
            System.out.println(m.group(i+1));
        }
        text = "productId=999993;isEffective=0;resourceId=;operateType=auth_product";
        m = inputPattern.matcher(text);
        System.out.println(" two="+m.groupCount()+";two="+m.matches());
        for(int i=0;i<m.groupCount();i++){
            System.out.println(m.group(i+1));
        }
    }
    @Test
    public void test() throws SerDeException {
        PpfunsSerDe ppfunsSerDe = new PpfunsSerDe();
        List<String> list = new ArrayList<>(23);
        list.add("eventsType");
        list.add("mac");
        list.add("sn");
        list.add("userId");
        list.add("userType");
        list.add("parentColumnId");
        list.add("columnId");
        list.add("contentId");
        list.add("contentType");
        list.add("operateType");
        list.add("timePosition");
        list.add("createTime");
        list.add("nowSpm");
        list.add("afterSpm");
        list.add("pos");
        list.add("posName");
        list.add("keyWord");
        list.add("code");
        list.add("message");
        list.add("productId");
        list.add("isEffective");
        list.add("resourceId");
        list.add("videoId");
        ppfunsSerDe.columnNames =list;
        List list2 = new ArrayList<>(23);
        List list3 = new ArrayList<>(23);
        for(int i=0;i<23;i++){
            list2.add(new PrimitiveTypeInfo());
            list3.add(null);
        }
        ppfunsSerDe.columnTypes = list2;
        ppfunsSerDe.row = list3;
        ppfunsSerDe.init();
        String text = "2019-11-01 00:59:53.665 INFO [resin-port-9003-55759][CommonAuthService.java:162] - Collection events:eventsType=auth_product;mac=208B3774BB30;sn=12022160605760208B3774BB30;userId=12050066;userType=VOD;contentId=13619;contentType=1;parentColumnId=6029;code=S100000;message=鉴权通过;operateType=auth_product;createTime=2019-11-01 00:59:53:END";
//        Object o =ppfunsSerDe.deserialize(new Text(text));
//        System.out.println(o);
    }
    @Test
    public void tst1(){
        String t = "13183.PAGE_RECORD.13347.0.1588039055813";
        System.out.println(t.matches("^[0-9]{1,}\\.[A-Za-z0-9_]{3,}\\.[0-9]{1,}\\.[0-9]{1,}\\.[0-9]{13}$"));
    }
}
