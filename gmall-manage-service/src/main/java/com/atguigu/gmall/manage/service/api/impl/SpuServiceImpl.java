package com.atguigu.gmall.manage.service.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.entity.PmsProductImage;
import com.atguigu.gmall.entity.PmsProductInfo;
import com.atguigu.gmall.entity.PmsProductSaleAttr;
import com.atguigu.gmall.entity.PmsProductSaleAttrValue;
import com.atguigu.gmall.manage.mapper.ProductImageMapper;
import com.atguigu.gmall.manage.mapper.ProductInfoMapper;
import com.atguigu.gmall.manage.mapper.ProductSaleAttrMapper;
import com.atguigu.gmall.manage.mapper.ProductSaleAttrValueMapper;
import com.atguigu.gmall.service.api.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class SpuServiceImpl implements SpuService {
    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private ProductSaleAttrMapper productSaleAttrMapper;

    @Autowired
    private ProductSaleAttrValueMapper productSaleAttrValueMapper;

    @Autowired
    private ProductImageMapper productImageMapper;


    @Override
    public List<PmsProductInfo> getProductByCatalog3Id(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        return productInfoMapper.select(pmsProductInfo);
    }

    @Override
    public void saveSpuInfo(PmsProductInfo pmsProductInfo) {
        pmsProductInfo.setProductName(pmsProductInfo.getSpuName());
        //插入spu商品信息
        productInfoMapper.insertSelective(pmsProductInfo);
        //返回插入的主键id
        String productId = pmsProductInfo.getId();
        //得到spu的销售属性集合
        List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
        //遍历
        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {
            pmsProductSaleAttr.setProductId(productId);
            //添加spu商品信息的销售属性
            productSaleAttrMapper.insertSelective(pmsProductSaleAttr);
            //根据销售属性get对应的值
            List<PmsProductSaleAttrValue> spuSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
            //遍历
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : spuSaleAttrValueList) {
                pmsProductSaleAttrValue.setProductId(productId);
                productSaleAttrValueMapper.insertSelective(pmsProductSaleAttrValue);
            }

        }
        //获得spu商品属性对应的图片集合
        List<PmsProductImage> spuImageList = pmsProductInfo.getSpuImageList();
        for (PmsProductImage pmsProductImage : spuImageList) {
            pmsProductImage.setProductId(productId);
            productImageMapper.insertSelective(pmsProductImage);
        }



    }

    @Override
    public List<PmsProductSaleAttr> getSaleAttrBySpuId(String spuId) {
        PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
        pmsProductSaleAttr.setProductId(spuId);
        List<PmsProductSaleAttr> productSaleAttrs = productSaleAttrMapper.select(pmsProductSaleAttr);
        for (PmsProductSaleAttr productSaleAttr : productSaleAttrs) {
            PmsProductSaleAttrValue productSaleAttrValue = new PmsProductSaleAttrValue();
            productSaleAttrValue.setProductId(spuId);
            productSaleAttrValue.setSaleAttrId(productSaleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> productSaleAttrValues = productSaleAttrValueMapper.select(productSaleAttrValue);
            productSaleAttr.setSpuSaleAttrValueList(productSaleAttrValues);
        }
        return productSaleAttrs;
    }

    @Override
    public List<PmsProductImage> getProductImageBySpuid(String spuId) {
        PmsProductImage pmsProductImage = new PmsProductImage();
        pmsProductImage.setProductId(spuId);
        List<PmsProductImage> select = productImageMapper.select(pmsProductImage);
        return select;
    }






}
