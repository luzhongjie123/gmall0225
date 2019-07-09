package com.atguigu.gmall.manage.service.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.entity.PmsBaseCatalog1;
import com.atguigu.gmall.entity.PmsBaseCatalog2;
import com.atguigu.gmall.entity.PmsBaseCatalog3;
import com.atguigu.gmall.manage.mapper.Catalog1Mapper;
import com.atguigu.gmall.manage.mapper.Catalog2Mapper;
import com.atguigu.gmall.manage.mapper.Catalog3Mapper;
import com.atguigu.gmall.service.api.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private Catalog1Mapper catalog1Mapper;
    @Autowired
    private Catalog2Mapper catalog2Mapper;
    @Autowired
    private Catalog3Mapper catalog3Mapper;


    @Override
    public List<PmsBaseCatalog1> getPmsBaseCatalog1s() {
        return catalog1Mapper.selectAll();
    }

    @Override
    public List<PmsBaseCatalog2> getPmsBaseCatalog2s(String Catalog1) {
        PmsBaseCatalog2 pmsBaseCatalog2 = new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(Catalog1);
        return catalog2Mapper.select(pmsBaseCatalog2);
    }

    @Override
    public List<PmsBaseCatalog3> getPmsBaseCatalog3s(String Catalog2) {
        PmsBaseCatalog3 pmsBaseCatalog3 = new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2Id(Catalog2);
        return catalog3Mapper.select(pmsBaseCatalog3);
    }
}
