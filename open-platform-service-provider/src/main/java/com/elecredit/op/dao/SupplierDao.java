package com.elecredit.op.dao;

import com.elecredit.common.dao.BaseDao;
import com.elecredit.op.model.Supplier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SupplierDao extends BaseDao<Supplier> {
    public JdbcTemplate getJdbcTemplate(){
        return this.jdbcTemplate;
    }
}
