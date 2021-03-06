package com.zijincaifu.service.impl.customer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.cache.manager.CacheLevel;
import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.dao.customer.ICustomerDao;
import com.zijincaifu.crm.dao.customer.IInvestItemDao;
import com.zijincaifu.crm.dao.customer.ITrackRecordDao;
import com.zijincaifu.crm.dao.personnel.IPersonnelDao;
import com.zijincaifu.crm.entity.customer.CustomerEntity;
import com.zijincaifu.crm.entity.customer.InvestItemEntity;
import com.zijincaifu.crm.entity.customer.RecommendEntity;
import com.zijincaifu.crm.enu.customer.CustomerLevelEnum;
import com.zijincaifu.crm.enu.customer.InvestItemStateEnum;
import com.zijincaifu.model.customer.CustomerQuery;
import com.zijincaifu.model.customer.OpenCustomerModel;
import com.zijincaifu.service.customer.ICustomerService;
import com.zijincaifu.service.customer.IInvestItemService;
import com.zijincaifu.service.customer.IRecommendService;

@Service
public class CustomerServiceImpl implements ICustomerService
{
    @Autowired
    private ICustomerDao customerDao;
    
    @Autowired
    private IInvestItemService itemService;
    
    @Autowired
    private IInvestItemDao investemDao;
    
    @Autowired
    private ITrackRecordDao trackRecordDao;
    
    @Autowired
    private IRecommendService recommendService;
    
    @Autowired
    private IPersonnelDao personnelDao;
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<CustomerEntity> queryCustomer(CustomerQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<CustomerEntity> condition = new QueryCondition<CustomerEntity>();
            condition.setPage(query);
            condition.addAllCondition(query);
            List<CustomerEntity> list = customerDao.queryCustomerList(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查询客户列表错误", e);
        }
    }
    
    @Override
    @Transactional
    public boolean addWeixinCustomer(OpenCustomerModel model)
            throws ServiceException
    {
        try
        {
            if (model == null)
            {
                throw new ServiceException("新增微信客户失败");
            }
            boolean isAdd = false;
            CustomerEntity customer = new CustomerEntity();
            customer.setName(model.getName());
            customer.setSex(model.getSex());
            customer.setPhone(model.getPhone());
            customer.setEmployeId(model.getEmployeeId());
            customer.setState(0);
            customer.setLevel(CustomerLevelEnum.NEW);
            customer.setUnionId(model.getUnionId());
            customer.setChannelId(model.getChannelId());
            customer.setCreateTime(new Date());
            
            CustomerQuery query = new CustomerQuery();
            query.setPhone(customer.getPhone());
            List<CustomerEntity> list = queryCustomer(query);
            
            if (list == null || list.size() == 0)
            { // 获取出生年月日   
                if (StringUtils.isNotEmpty(customer.getCardNo()))
                {
                    String birthday = customer.getCardNo().substring(6, 14);
                    Date birthdate = null;
                    birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
                    customer.setBirthday(birthdate);
                }
                customerDao.addCustomer(customer);
                HierarchicalCacheManager.set(CacheLevel.REDIS,
                        "CRM_CUSTOMER_LEVEL_AD",
                        customer.getCustomerId(),
                        "1");
                isAdd = true;
            }
            else
            {
                customer = list.get(0);
                customer.setUnionId(model.getUnionId());
                customerDao.updateCustomer(customer);
            }
            
            InvestItemEntity item = new InvestItemEntity();
            item.setEmployeId(model.getEmployeeId());
            item.setCustomerId(customer.getCustomerId());
            item.setProductId(model.getProductId());
            item.setChannelId(model.getChannelId());
            item.setState(InvestItemStateEnum.REGIST);
            itemService.add(item);
            
            //PersonnelEntity employe = personnelDao.getPersonnel(model.getEmployeeId());
            List<RecommendEntity> recommen = model.getRecommen();
            int sub = 1;
            for (Iterator<RecommendEntity> iterator = recommen.iterator(); iterator.hasNext();)
            {
                RecommendEntity recommendEntity = iterator.next();
                if (recommendEntity.getLevel() == 1)
                {
                    if (StringUtils.isEmpty(recommendEntity.getUnionId()))
                    {
                        iterator.remove();
                        sub = 0;
                        continue;
                    }
                    //                    recommendEntity.setParentId(employe.getUnionId());
                    //                    if (recommendEntity.getUnionId()
                    //                            .equals(employe.getUnionId()))
                    //                    {
                    //                        
                    //                        continue;
                    //                    }
                }
                recommendEntity.setLevel(recommendEntity.getLevel() + sub);
                recommendEntity.setInvestId(item.getId());
            }
            recommendService.addRecommend(recommen);
            return isAdd;
        }
        catch (Exception e)
        {
            
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增微信客户信息错误", e);
        }
    }
    
    @Override
    @Transactional
    public boolean addCustomer(CustomerEntity customer, String productId)
            throws ServiceException
    {
        try
        {
            if (customer == null)
            {
                throw new ServiceException("客户信息为空");
            }
            else
            {
                String employeId = customer.getEmployeId();
                CustomerQuery query = new CustomerQuery();
                query.setPhone(customer.getPhone());
                List<CustomerEntity> list = queryCustomer(query);
                boolean isAdd = false;
                if (list == null || list.size() == 0)
                {
                    // 获取出生年月日   
                    if (StringUtils.isNotEmpty(customer.getCardNo()))
                    {
                        String birthday = customer.getCardNo().substring(6, 14);
                        Date birthdate = null;
                        birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
                        customer.setBirthday(birthdate);
                    }
                    customerDao.addCustomer(customer);
                    isAdd = true;
                }
                else
                {
                    customer = list.get(0);
                }
                InvestItemEntity item = new InvestItemEntity();
                item.setCustomerId(customer.getCustomerId());
                item.setEmployeId(employeId);
                item.setProductId(productId);
                item.setChannelId("AAA001");
                item.setState(InvestItemStateEnum.REGIST);
                itemService.add(item);
                
                //添加推荐明细
                //                PersonnelEntity employe = personnelDao.getPersonnel(customer.getEmployeId());
                //                RecommendEntity recommendEntity = new RecommendEntity();
                //                recommendEntity.setChannelId(item.getChannelId());
                //                recommendEntity.setInvestId(item.getId());
                //                recommendEntity.setLevel(1);
                //                recommendEntity.setParentId("0");
                //                recommendEntity.setName(employe.getName());
                //                recommendEntity.setUid(customer.getEmployeId());
                //                recommendEntity.setUnionId(employe.getUnionId());
                //                List<RecommendEntity> recommendList = new ArrayList<>();
                //                recommendList.add(recommendEntity);
                //                recommendService.addRecommend(recommendList);
                
                return isAdd;
            }
            
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增客户信息错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public void modifyCustomer(CustomerEntity customer, String uId)
            throws ServiceException
    {
        try
        {
            if (customer != null)
            {
                // 获取出生年月日   
                if (StringUtils.isNotEmpty(customer.getCardNo()))
                {
                    String birthday = customer.getCardNo().substring(6, 14);
                    Date birthdate = null;
                    birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
                    customer.setBirthday(birthdate);
                }
                CustomerEntity temCust=getCustomer(customer.getCustomerId());
                if (StringUtil.isBlank(temCust.getPhoneStr()))
                {
                    customer.setPhoneStr(temCust.getPhone());
                }
                else
                {
                    customer.setPhoneStr(temCust.getPhoneStr() + ","
                            + temCust.getPhone());
                }
                updateCustomer(customer, uId);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
        
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public CustomerEntity getCustomer(String customerId)
    {
        return customerDao.getCustomer(customerId);
    }
    
    @Override
    @Transactional
    public void updateCustomer(CustomerEntity customer, String uId)
    {
        try
        {
            CustomerEntity oldCustomer = this.getCustomer(customer.getCustomerId());
            //            if (uId.equals("") || oldCustomer.getEmployeId().equals("")
            //                    || oldCustomer.getEmployeId().equals(uId))
            //            {
            customerDao.updateCustomer(customer);
            //            }
            //            else
            //            {
            //                throw new ServiceException("该客户不属于当前员工");
            //            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional
    public void deleteCustomer(String customerId)
    {
        try
        {
            CustomerEntity customer = this.getCustomer(customerId);
            customerDao.deleteCustomer(customer.getId());
            investemDao.deleteItems(customerId);
            trackRecordDao.deleteTrackRecord(customerId);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("删除客户错误", e);
        }
    }
    
}
