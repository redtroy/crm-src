package com.zijincaifu.crm.dao.customer;

import java.util.List;

import com.zijincaifu.crm.entity.customer.OrganizationEntity;

public interface IOrganizationDao
{
    /**
     * 获取层级信息
     * @param uid
     */
     public List<OrganizationEntity> queryOrg(String parentId);

    public OrganizationEntity getOrg(String orgId);
}
