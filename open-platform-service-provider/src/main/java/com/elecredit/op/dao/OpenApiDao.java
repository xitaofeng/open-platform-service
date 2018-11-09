package com.elecredit.op.dao;

import com.elecredit.common.dao.BaseDao;
import com.elecredit.op.model.OpenApi;
import jodd.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class OpenApiDao extends BaseDao<OpenApi> {

    public List<OpenApi> getByEntity(Long entityId) {
        return findList("select * from op_open_api where entity_id = ? order by app_id",entityId);
    }

    public List<OpenApi> getByEntity(Long... entityIdList){
        String[] placeholders = new String[entityIdList.length];
        Arrays.fill(placeholders,"?");
        return findList("select * from op_open_api where entity_id in ("+ StringUtil.join(placeholders,",") +")",entityIdList);
    }
    public List<OpenApi> getByEntity(List<Long> entityIdList){
        String[] placeholders = new String[entityIdList.size()];
        Object[] data = new Long[entityIdList.size()];
        for (int i = 0; i < entityIdList.size(); i++) {
            data[i] = entityIdList.get(i);
        }
        Arrays.fill(placeholders,"?");
        return findList("select * from op_open_api where entity_id in ("+ StringUtil.join(placeholders,",") +")",data);
    }

}
