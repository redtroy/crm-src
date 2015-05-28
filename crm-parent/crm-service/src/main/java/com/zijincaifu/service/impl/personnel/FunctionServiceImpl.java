package com.zijincaifu.service.impl.personnel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.dao.personnel.IFunctionDao;
import com.zijincaifu.crm.entity.personnel.FunctionEntity;
import com.zijincaifu.model.personnel.FunctionModel;
import com.zijincaifu.service.personnel.IFunctionService;

@Service
public class FunctionServiceImpl implements IFunctionService
{
    @Autowired
    private IFunctionDao functiondao;
    
    /**
     * 获取左侧菜单
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<FunctionModel> queryTreeFunctions() throws ServiceException
    {
        try
        {
            QueryCondition<FunctionEntity> query = new QueryCondition<FunctionEntity>();
            query.addCondition("parentId", "0");
            List<FunctionEntity> functionList = functiondao.queryFunction(query);
            List<FunctionModel> list = new ArrayList<FunctionModel>();
            for (FunctionEntity functionEntity : functionList)
            {
                if (functionEntity == null)
                {
                    continue;
                }
                QueryCondition<FunctionEntity> childrenQuery = new QueryCondition<FunctionEntity>();
                childrenQuery.addCondition("parentId", functionEntity.getId());
                List<FunctionEntity> childrenList = functiondao.queryFunction(childrenQuery);
                FunctionModel model = new FunctionModel();
                model.setFunction(functionEntity);
                model.setChildren(childrenList);
                list.add(model);
                
            }
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询所有系统菜单错误", e, this.getClass());
            throw new ServiceException("查询所有系统菜单错误", e);
        }
        
    }
}
