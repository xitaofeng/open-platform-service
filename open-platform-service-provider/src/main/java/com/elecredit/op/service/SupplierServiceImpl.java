package com.elecredit.op.service;

import com.elecredit.op.dao.SupplierDao;
import com.elecredit.op.dao.SupplierServiceDao;
import com.elecredit.op.model.DataService;
import com.elecredit.op.model.Supplier;
import com.elecredit.op.model.SupplierServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 渠道服务实现类
 */
@Component
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private SupplierServiceDao supplierServiceDao;
    @Autowired
    private SupplierServiceConfigService supplierServiceConfigService;

    @Override
    public Supplier getById(Long id) {
        return getById(id,true);
    }

    @Override
    public Supplier getById(Long id, boolean withDataService) {

        Supplier supplier = supplierDao.getById(id);
        if(supplier != null){
            if (withDataService) {
                supplier.setServiceList(supplierServiceDao.getBySupplierId(id));
            }
        }
        return supplier;
    }

    @Override
    public List<Supplier> getAll() {
        return getAll(true);
    }

    @Override
    public List<Supplier> getAll(boolean withDataService) {

        List<Supplier> supplierList = supplierDao.findList("select * from op_supplier order by supplier_id");
        if(supplierList != null && withDataService)
            for(Supplier supplier : supplierList){
                supplier.setServiceList(supplierServiceDao.getBySupplierId(supplier.getSupplierId()));
            }

        return supplierList;
    }

    @Override
    public Supplier getByName(String name) {
        return supplierDao.findOne("select * from op_supplier where supplier_name = ?", name);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Supplier save(Supplier supplier) {
        try {
            List<DataService> dataServiceList = supplier.getServiceList();
            if(dataServiceList != null)
                for(DataService dataService : dataServiceList){
                    SupplierServiceConfig supplierServiceConfig = new SupplierServiceConfig();
                    supplierServiceConfig.setServiceId(dataService.getServiceId());
                    supplierServiceConfig.setSupplierId(supplier.getSupplierId());
                    supplierServiceDao.save(supplierServiceConfig);
                }
            supplierDao.save(supplier);
            return supplier;
        }catch (DuplicateKeyException e){
                throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Supplier update(Supplier supplier) {
        List<Long> serviceIds = new ArrayList<>();
        supplierServiceDao.update("delete from op_supplier_service where supplier_id = ? ",supplier.getSupplierId());
        List<DataService> dataServiceList = supplier.getServiceList();
        if(dataServiceList != null) {
            for (DataService dataService : dataServiceList) {
//                SupplierServiceConfig supplierServiceConfig = new SupplierServiceConfig();
//                supplierServiceConfig.setServiceId(dataService.getServiceId());
//                supplierServiceConfig.setSupplierId(supplier.getSupplierId());
//                supplierServiceDao.save(supplierServiceConfig);
                serviceIds.add(dataService.getServiceId());
            }
            supplierServiceConfigService.updateServiceList(supplier.getSupplierId(),serviceIds);
        }
        return supplierDao.update(supplier);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Supplier supplier) {
        return deleteById(supplier.getSupplierId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {

        supplierServiceDao.update("delete from op_supplier_service where supplier_id = ?",id);
        return supplierDao.deleteById(id);
    }

    @Override
    public List<Supplier> getByPage(int pageNum, int pageSize) {
        return getByPage(pageNum,pageSize, true);
    }

    @Override
    public List<Supplier> getByPage(int pageNum, int pageSize, boolean withDataService) {
        int offset = pageNum * pageSize;

        List<Supplier> supplierList = supplierDao.findList("select * from op_supplier order by created_time desc limit " + offset + "," + pageSize);
        if(supplierList != null && withDataService)
            for(Supplier supplier : supplierList)
                supplier.setServiceList(supplierServiceDao.getBySupplierId(supplier.getSupplierId()));

        return supplierList;
    }

    @Override
    public Long count() {
        return supplierDao.count("");
    }
}
