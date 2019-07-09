package com.atguigu.gmall.service.api;

import com.atguigu.gmall.entity.PmsProductImage;
import com.atguigu.gmall.entity.PmsProductInfo;
import com.atguigu.gmall.entity.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {


    List<PmsProductInfo> getProductByCatalog3Id(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> getSaleAttrBySpuId(String spuId);

    List<PmsProductImage> getProductImageBySpuid(String spuId);



}
