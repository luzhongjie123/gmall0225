package com.atguigu.gmall.service.api;

import com.atguigu.gmall.entity.PmsBaseAttrInfo;
import com.atguigu.gmall.entity.PmsBaseAttrValue;
import com.atguigu.gmall.entity.PmsBaseSaleAttr;

import java.util.HashSet;
import java.util.List;

public interface AttrInfoService {


    List<PmsBaseAttrInfo> getAll(String catalog3Id);

    void saveAttrInfoValue( PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> getBaseSaleAttrs();

    List<PmsBaseAttrInfo> getAttrValueById(HashSet<String> skuAttrValueIds);
}
