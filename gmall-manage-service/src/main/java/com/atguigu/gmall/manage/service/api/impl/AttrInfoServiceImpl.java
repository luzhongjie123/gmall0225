package com.atguigu.gmall.manage.service.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.entity.PmsBaseAttrInfo;
import com.atguigu.gmall.entity.PmsBaseAttrValue;
import com.atguigu.gmall.entity.PmsBaseSaleAttr;
import com.atguigu.gmall.manage.mapper.AttrInfoMapper;
import com.atguigu.gmall.manage.mapper.AttrValueMapper;
import com.atguigu.gmall.manage.mapper.SaleAttrMapper;
import com.atguigu.gmall.service.api.AttrInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
@Service
public class AttrInfoServiceImpl implements AttrInfoService {
    @Autowired
    private AttrInfoMapper attrInfoMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    @Autowired
    private SaleAttrMapper saleAttrMapper;

    @Override
    public List<PmsBaseAttrInfo> getAll(String catalog3Id) {
        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> PmsBaseAttrInfos = attrInfoMapper.select(pmsBaseAttrInfo);
        for (PmsBaseAttrInfo baseAttrInfo : PmsBaseAttrInfos) {
            String baseAttrInfoId = baseAttrInfo.getId();
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(baseAttrInfoId);
            List<PmsBaseAttrValue> pmsBaseAttrValues = attrValueMapper.select(pmsBaseAttrValue);
            baseAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }
        return PmsBaseAttrInfos;
    }

    @Override
    public void saveAttrInfoValue(PmsBaseAttrInfo pmsBaseAttrInfo) {

        if(StringUtils.isEmpty(pmsBaseAttrInfo.getId())){
            //id为空
            attrInfoMapper.insertSelective(pmsBaseAttrInfo);
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                attrValueMapper.insert(pmsBaseAttrValue);
            }
        }else{
            //注意:id不能空
            //根据属性id修改属性名称
            attrInfoMapper.updateByPrimaryKeySelective(pmsBaseAttrInfo);
            //获取属性对应值的集合
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            //先将属性对应的值全部删除,然后重新添加
            PmsBaseAttrValue pmsBaseAttrValue1 = new PmsBaseAttrValue();
            pmsBaseAttrValue1.setAttrId(pmsBaseAttrInfo.getId());
            attrValueMapper.delete(pmsBaseAttrValue1);
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                attrValueMapper.insertSelective(pmsBaseAttrValue);
            }
        }

    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        List<PmsBaseAttrValue> select = attrValueMapper.select(pmsBaseAttrValue);
        return select;
    }

    @Override
    public List<PmsBaseSaleAttr> getBaseSaleAttrs() {
        return saleAttrMapper.selectAll();
    }

    @Override
    public List<PmsBaseAttrInfo> getAttrValueById(HashSet<String> skuAttrValueIds) {
        String valueIdsStr=StringUtils.join(skuAttrValueIds,",");
        List<PmsBaseAttrInfo> pmsBaseAttrInfos =attrInfoMapper.selectAttrValueById(valueIdsStr);
        return pmsBaseAttrInfos;
    }


}
