package com.atguigu.gmall.service.api;

import com.atguigu.gmall.entity.PmsBaseCatalog1;
import com.atguigu.gmall.entity.PmsBaseCatalog2;
import com.atguigu.gmall.entity.PmsBaseCatalog3;

import java.util.List;

public interface CatalogService {
    public List<PmsBaseCatalog1> getPmsBaseCatalog1s() ;

    public List<PmsBaseCatalog2> getPmsBaseCatalog2s(String Catalog1) ;

    public List<PmsBaseCatalog3> getPmsBaseCatalog3s(String Catalog2) ;

}
