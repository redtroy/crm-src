package com.zijincaifu.dao.personnel;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.zijincaifu.entity.personnel.PersonnelEntity;

public interface IPersonnelDao {
	/**
	 * 新增员工
	 * @param personnel
	 */
	@Insert
	public void addPersonnel(PersonnelEntity personnel);
	
	/**
	 * 修改员工
	 * @param personnel
	 */
	@Update
	public void updatePersonnel(PersonnelEntity personnel);
		
	/**
	 * 获取员工信息
	 * @param id
	 */
	@Get
	public PersonnelEntity getPersonnel(String id);
	
	/**
	 * 查看员工
	 * @param query
	 */
	public List<PersonnelEntity> queryPersonnel(PersonnelEntity personnel);
	
	/**
	 * 冻结员工
	 * @param id
	 */
	public void freezePersonnel(String id);
	
	/**
	 * 解冻员工
	 * @param id
	 */
	public void unFreezePersonnel(String id);
	
	/**
	 * 初始化员工密码
	 * @param id
	 */
	public void initPassword(String id);

}
