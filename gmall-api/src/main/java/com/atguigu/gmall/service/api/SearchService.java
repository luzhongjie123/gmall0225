package com.atguigu.gmall.service.api;

import com.atguigu.gmall.entity.Page;
import com.atguigu.gmall.entity.PmsSearchSkuInfo;
import com.atguigu.gmall.entity.PmsSkuInfo;
import com.atguigu.gmall.entity.SkuParam;

import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> getPmsSearchSkuInfoBySkuParam(SkuParam skuParam);
}
