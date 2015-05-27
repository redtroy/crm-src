package com.zijincaifu.service.impl.customer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.dao.customer.ICustomerDao;
import com.zijincaifu.crm.dao.customer.IInvestItemDao;
import com.zijincaifu.crm.dao.customer.ITrackRecordDao;
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
            CustomerEntity customer = new CustomerEntity();
            customer.setName(model.getName());
            customer.setSex(model.getSex());
            customer.setPhone(model.getPhone());
            customer.setEmployeId(model.getEmployeeId());
            customer.setState(0);
            customer.setLevel(CustomerLevelEnum.NEW);
            customer.setUnionId(model.getUnionId());
            
            CustomerQuery query = new CustomerQuery();
            query.setPhone(customer.getPhone());
            List<CustomerEntity> list = queryCustomer(query);
            
            if (list == null || list.size() == 0)
            { // 获取出生年月日   
                if (customer.getCardNo() != null)
                {
                    String birthday = customer.getCardNo().substring(6, 14);
                    Date birthdate = null;
                    birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
                    customer.setBirthday(birthdate);
                }
                customerDao.addCustomer(customer);
            }
            InvestItemEntity item = new InvestItemEntity();
            item.setCustomerId(list.get(0).getCustomerId());
            item.setProductId(model.getProductId());
            item.setChannelId(list.get(0).getChannelId());
            item.setState(InvestItemStateEnum.REGIST);
            itemService.add(item);
            
            List<RecommendEntity> recommen = model.getRecommen();
            for (Iterator<RecommendEntity> iterator = recommen.iterator(); iterator.hasNext();)
            {
                RecommendEntity recommendEntity = iterator.next();
                recommendEntity.setInvestId(item.getId());
            }
            recommendService.addRecommend(recommen);
            
        }
        catch (Exception e)
        {
            
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增客户信息错误", e);
        }
        return false;
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
                CustomerQuery query = new CustomerQuery();
                query.setPhone(customer.getPhone());
                List<CustomerEntity> list = queryCustomer(query);
                boolean isAdd = false;
                if (list == null || list.size() == 0)
                {
                    // 获取出生年月日   
                    if (customer.getCardNo() != null)
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
                item.setProductId(productId);
                item.setChannelId(customer.getChannelId());
                item.setState(InvestItemStateEnum.REGIST);
                itemService.add(item);
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
    public void modifyCustomer(CustomerEntity customer,String uId) throws ServiceException
    {
        try
        {
            if (customer != null)
            {
                // 获取出生年月日   
                if (customer.getCardNo() != null)
                {
                    String birthday = customer.getCardNo().substring(6, 14);
                    Date birthdate = null;
                    birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
                    customer.setBirthday(birthdate);
                }
                updateCustomer(customer,uId);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增客户信息错误", e);
        }
        
    }
    
    @Override
    public CustomerEntity getCustomer(String customerId)
    {
        return customerDao.getCustomer(customerId);
    }
    
    @Override
    public void updateCustomer(CustomerEntity customer,String uId)
    {
        try
        {
            CustomerEntity oldCustomer=this.getCustomer(customer.getCustomerId());
            if(uId.equals("")||oldCustomer.getEmployeId().equals("")||oldCustomer.getEmployeId().equals(uId)){
                customerDao.updateCustomer(customer);
            }else{
                throw new ServiceException("该客户已经变更归属");
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    @Override
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
