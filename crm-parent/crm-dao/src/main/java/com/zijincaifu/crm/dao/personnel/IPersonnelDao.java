package com.zijincaifu.crm.dao.personnel;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;

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
	 * @param uid
	 */
	public PersonnelEntity getPersonnel(String uid);
	
	/**
	 * 查看员工
	 * @param query
	 */
	public List<PersonnelEntity> queryPersonnel(QueryCondition<PersonnelEntity> query);

    public List<PersonnelEntity> autoPersonnel(
            QueryCondition<PersonnelEntity> condition);

    public PersonnelEntity getPersonnelByPhone(String phone);
	

}
