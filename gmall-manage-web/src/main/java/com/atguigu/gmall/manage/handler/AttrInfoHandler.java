package com.atguigu.gmall.manage.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.entity.PmsBaseAttrInfo;
import com.atguigu.gmall.entity.PmsBaseAttrValue;
import com.atguigu.gmall.entity.PmsBaseSaleAttr;
import com.atguigu.gmall.service.api.AttrInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
public class AttrInfoHandler {
    @Reference
    private AttrInfoService attrInfoService;

    /**
     * 查询全部的平台属性
     * @param catalog3Id
     * @return
     */
    @ResponseBody
    @RequestMapping("/attrInfoList")
    public List<PmsBaseAttrInfo> getAll(String catalog3Id){
        List<PmsBaseAttrInfo> attrInfo= attrInfoService.getAll(catalog3Id);
        return attrInfo;
    }

    /**
     * 添加平台属性
     * @param pmsBaseAttrInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("saveAttrInfo")
    public String addAttrInfoValue(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        attrInfoService.saveAttrInfoValue(pmsBaseAttrInfo);
        return  "success";
    }

    /**
     * 根据平台属性id查询对应属性值
     * @param attrId
     * @return
     */
    @ResponseBody
    @RequestMapping("getAttrValueList")
    public List<PmsBaseAttrValue>  getAttrValueList(String attrId){
        return attrInfoService.getAttrValueList(attrId);
    }

    @ResponseBody
    @RequestMapping("baseSaleAttrList")
    public List<PmsBaseSaleAttr> getBaseSaleAttrs(){
        return  attrInfoService.getBaseSaleAttrs();
    }




}
