package com.example.service.sys;

import com.example.dao.sys.PermissionMapper;
import com.example.dao.sys.RoleMapper;
import com.example.dao.sys.RolePermissionMapper;
import com.example.dto.PermissionVO;
import com.example.dto.RoleVO;
import com.example.model.base.LayerTree;
import com.example.model.sys.Permission;
import com.example.model.sys.Role;
import com.example.model.sys.RolePermissionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @项目名称：wyait-manage
 * @包名：com.wyait.manage.service
 * @类描述：
 * @创建人：wyait
 * @创建时间：2017-12-20 15:53
 * @version：V1.0
 */
@Service
public class AuthService {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthService.class);
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	public int addPermission(Permission permission) {
		return this.permissionMapper.insert(permission);
	}

	public List<Permission> permList() {
		return this.permissionMapper.findAll();
	}

	public int updatePerm(Permission permission) {
		return this.permissionMapper.updateByPrimaryKeySelective(permission);
	}

	public Permission getPermission(int id) {
		return this.permissionMapper.selectByPrimaryKey(id);
	}

	public String delPermission(int id) {
		//查看该权限是否有子节点，如果有，先删除子节点
		List<Permission> childPerm = this.permissionMapper.findChildPerm(id);
		if(null != childPerm && childPerm.size()>0){
			return "删除失败，请您先删除该权限的子节点";
		}
		if(this.permissionMapper.deleteByPrimaryKey(id)>0){
			return "ok";
		}else{
			return "删除失败，请您稍后再试";
		}
	}

	public List<Role> roleList() {
		return this.roleMapper.findList();
	}

	public List<PermissionVO> findPerms() {
		return this.permissionMapper.findPerms();
	}

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
	public String addRole(Role role, String permIds) {
		this.roleMapper.insert(role);
		int roleId=role.getId();
		String[] arrays=permIds.split(",");
		logger.debug("权限id =arrays="+arrays.toString());
		setRolePerms(roleId, arrays);
		return "ok";
	}

	public RoleVO findRoleAndPerms(Integer id) {
		return this.roleMapper.findRoleAndPerms(id);
	}

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
	public String updateRole(Role role, String permIds) {
		int roleId=role.getId();
		String[] arrays=permIds.split(",");
		logger.debug("权限id =arrays="+arrays.toString());
		//1，更新角色表数据；
		int num=this.roleMapper.updateByPrimaryKeySelective(role);
		if(num<1){
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "操作失败";
		}
		//2，删除原角色权限；
		batchDelRolePerms(roleId);
		//3，添加新的角色权限数据；
		setRolePerms(roleId, arrays);
		return "ok";
	}



	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
	public String delRole(int id) {
		//1.删除角色对应的权限
		batchDelRolePerms(id);
		//2.删除角色
		int num=this.roleMapper.deleteByPrimaryKey(id);
		if(num<1){
			//事务回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "操作失败";
		}
		return "ok";
	}

	public List<Role> getRoles() {
		//TODO 根据部门和权限等级限制角色显示
		return this.roleMapper.getRoles();
	}

	public List<Role> getRoleByUser(Integer userId) {
		return this.roleMapper.getRoleByUserId(userId);
	}
	public List<Permission> findPermsByRoleId(Integer id) {
		return this.permissionMapper.findPermsByRole(id);
	}

	public List<PermissionVO> getUserPerms(Integer id) {
		return this.permissionMapper.getUserPerms(id);
	}

	/**
	 * 批量删除角色权限中间表数据
	 * @param roleId
	 */
	private void batchDelRolePerms(int roleId) {
		List<RolePermissionKey> rpks=this.rolePermissionMapper.findByRole(roleId);
		if(null!=rpks && rpks.size()>0){
			for (RolePermissionKey rpk : rpks) {
				this.rolePermissionMapper.deleteByPrimaryKey(rpk);
			}
		}
	}

	/**
	 * 给当前角色设置权限
	 * @param roleId
	 * @param arrays
	 */
	private void setRolePerms(int roleId, String[] arrays) {
		for (String permid : arrays) {
			RolePermissionKey rpk=new RolePermissionKey();
			rpk.setRoleId(roleId);
			rpk.setPermitId(Integer.valueOf(permid));
			this.rolePermissionMapper.insert(rpk);
		}
	}

	public List<LayerTree> getPermissionAll(Integer roleId) {
		return getNode(0,roleId);
	}
	private List<LayerTree> getNode(Integer pid,Integer roleId){
		List<LayerTree> list  = this.permissionMapper.getPermissionAll(pid,roleId);
		if(!CollectionUtils.isEmpty(list)){
			for(LayerTree node : list){
				List<LayerTree> children =  getNode(Integer.valueOf(node.getId()),roleId);
				if(!CollectionUtils.isEmpty(children)){
					node.setChildren(children);
				}
			}
			return list;
		}
		return null;
	}
}
