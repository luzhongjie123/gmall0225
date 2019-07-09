package com.atguigu.gmall.service.api;

import com.atguigu.gmall.entity.PmsProductSaleAttr;
import com.atguigu.gmall.entity.PmsSkuInfo;
import com.atguigu.gmall.entity.PmsSkuSaleAttrValue;

import java.util.List;

public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuById(String skuId);


    List<PmsProductSaleAttr> getspuSaleAttrListCheckBySku(String productId, String skuId);

    List<PmsSkuInfo> getSkuInfoListBySpuId(String productId);

    List<PmsSkuInfo> getSkuAll();
}
