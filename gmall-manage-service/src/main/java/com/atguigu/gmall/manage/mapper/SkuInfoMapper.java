package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.entity.PmsSkuInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkuInfoMapper extends Mapper<PmsSkuInfo> {
    List<PmsSkuInfo> selectSkusByProductId(@Param("productId") String productId);
}
