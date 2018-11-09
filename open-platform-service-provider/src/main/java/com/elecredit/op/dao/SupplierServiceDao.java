package com.elecredit.op.dao;

import com.elecredit.common.dao.BaseDao;
import com.elecredit.op.model.DataService;
import com.elecredit.op.model.Supplier;
import com.elecredit.op.model.SupplierServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 渠道-服务
 */
@Component
public class SupplierServiceDao extends BaseDao<SupplierServiceConfig> {

    @Autowired
    private SupplierDao supplierDao;

    @Autowired
    private DataServiceDao dataServiceDao;

    public List<Supplier> getByServiceId(Long serviceId){
        return supplierDao.findList("SELECT a.* FROM open_platform.op_supplier a,open_platform.op_supplier_service b where a.supplier_id=b.supplier_id and b.service_id = ?",serviceId);
    }

    public List<DataService> getBySupplierId(Long supplierId){

        List<DataService> serviceList = new ArrayList<DataService>();
        List<SupplierServiceConfig> supplierServiceConfigList = findList("select * from op_supplier_service where supplier_id = ?",supplierId);
        if(supplierServiceConfigList != null)
            for(SupplierServiceConfig supplierServiceConfig : supplierServiceConfigList){

                DataService service = dataServiceDao.getById(supplierServiceConfig.getServiceId());
                if(service != null)
                    serviceList.add(service);
            }

        return serviceList;
    }
}
