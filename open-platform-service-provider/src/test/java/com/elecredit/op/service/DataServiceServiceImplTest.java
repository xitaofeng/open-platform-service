package com.elecredit.op.service;

import com.elecredit.SuperTest;
import com.elecredit.service.IDGenerateService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DataServiceServiceImplTest extends SuperTest {
    @Autowired
    private IDGenerateService idGenerateService;
    @Autowired
    private DataServiceService dataServiceService;


    @Test
    public void categoryList() {

//        List<String> list = new ArrayList<>();
//        list.add("工商企业");
//        dataServiceService.categoryMerge(list,"新工商企业");

    }
}