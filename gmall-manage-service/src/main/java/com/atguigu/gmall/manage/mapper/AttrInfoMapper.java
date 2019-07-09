package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.entity.PmsBaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AttrInfoMapper extends Mapper<PmsBaseAttrInfo> {
    List<PmsBaseAttrInfo> selectAttrValueById(@Param("valueIdsStr") String valueIdsStr);
}
