package com.elecredit.op.service;

import com.elecredit.op.dao.SupplierServiceDao;
import com.elecredit.op.model.DataService;
import com.elecredit.op.model.Supplier;
import com.elecredit.op.model.SupplierServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SupplierServiceConfigServiceImpl implements SupplierServiceConfigService {

    @Autowired
    private SupplierServiceDao supplierServiceDao;

    @Override
    public List<SupplierServiceConfig> getByServiceId(Long serviceId) {
        return supplierServiceDao.findList("select * from op_supplier_service a,op_supplier b where a.supplier_id=b.supplier_id and service_id = ?",serviceId);
    }

    @Override
    public List<SupplierServiceConfig> getBySupplierId(Long supplierId) {
        return supplierServiceDao.findList("select * from op_supplier_service where supplier_id = ?",supplierId);
    }

    @Override
    public boolean updateSupplierList(Long serviceId, List<Long> supplierIdList) {
        if(supplierIdList == null || supplierIdList.size() == 0){
            supplierServiceDao.update("delete from op_supplier_service where service_id=?",serviceId);
            return true;
        }
        List<Long> intersection = new ArrayList<>();
        List<Long> suppliers = new ArrayList<>();
        List<Supplier> supplierList = supplierServiceDao.getByServiceId(serviceId);
        if(supplierList != null){
            for(Supplier supplier :supplierList){
                suppliers.add(supplier.getSupplierId());
            }
            //计算交集
            for(Long supplierId : supplierIdList){
                if(suppliers.contains(supplierId)){
                    intersection.add(supplierId);
                }
            }
            if(intersection.size() != 0){
                for(Long supplierId : suppliers){
                    if(!intersection.contains(supplierId)){
                        supplierServiceDao.update("delete from op_supplier_service where service_id=? and supplier_id=?",serviceId,supplierId);
                    }
                }
                for(Long id : supplierIdList){
                    if(!intersection.contains(id)){
                        save(serviceId,id);
                    }
                }
            }else{
                for(Long supplierId : suppliers) {
                    supplierServiceDao.update("delete from op_supplier_service where service_id=? and supplier_id=?",serviceId,supplierId);
                }
                for(Long id : supplierIdList){
                    save(serviceId,id);
                }
            }
        }else{
            for(Long id : supplierIdList){
                save(serviceId,id);
            }
        }
        return true;
    }

    private void save(Long serviceId,Long supplierId){
        SupplierServiceConfig config = new SupplierServiceConfig();
        config.setServiceId(serviceId);
        config.setSupplierId(supplierId);
        config.setCost(BigDecimal.valueOf(0));
        supplierServiceDao.save(config);
    }

    @Override
    public boolean updateServiceList(Long supplierId, List<Long> dataServiceIdList) {
        if(dataServiceIdList == null || dataServiceIdList.size() == 0){
            supplierServiceDao.update("delete from op_supplier_service where supplier_id=?",supplierId);
            return true;
        }
        List<Long> intersection = new ArrayList<>();
        List<Long> services = new ArrayList<>();
        List<DataService> serviceList = supplierServiceDao.getBySupplierId(supplierId);
        if(serviceList != null){
            for(DataService service :serviceList){
                services.add(service.getServiceId());
            }
            //计算交集
            for(Long serviceId : dataServiceIdList){
                if(services.contains(serviceId)){
                    intersection.add(serviceId);
                }
            }
            if(intersection.size() != 0){
                for(Long serviceId : services){
                    if(!intersection.contains(serviceId)){
                        supplierServiceDao.update("delete from op_supplier_service where supplier_id=? and service_id=?",supplierId,serviceId);
                    }
                }
                for(Long serviceId : dataServiceIdList){
                    if(!intersection.contains(serviceId)){
                        save(serviceId,supplierId);
                    }
                }
            }else{
                for(Long serviceId : services) {
                    supplierServiceDao.update("delete from op_supplier_service where supplier_id=? and service_id=?",supplierId,serviceId);
                }
                for(Long serviceId : dataServiceIdList){
                    save(serviceId,supplierId);
                }
            }
        }else{
            for(Long serviceId : dataServiceIdList){
                save(serviceId,supplierId);
            }
        }
        return true;
    }

    @Override
    public List<Supplier> getByPage(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Long count() {
        return null;
    }
}
