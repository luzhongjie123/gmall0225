package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.entity.PmsProductSaleAttr;
import com.atguigu.gmall.entity.PmsSkuSaleAttrValue;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkuSaleAttrValueMapper  extends Mapper<PmsSkuSaleAttrValue> {
    List<PmsSkuSaleAttrValue> selectByIds(@Param("ids") Integer[] ids);

    List<PmsProductSaleAttr> selectSpuSaleAttrListCheckBySku(@Param("productId") String productId, @Param("skuId")String skuId);
}
