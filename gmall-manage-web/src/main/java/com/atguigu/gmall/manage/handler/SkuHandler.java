package com.atguigu.gmall.manage.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.entity.PmsSkuInfo;
import com.atguigu.gmall.service.api.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@Controller
public class SkuHandler {
    @Reference
    private SkuService  skuService;


    @ResponseBody
    @RequestMapping("saveSkuInfo")
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){
        pmsSkuInfo.setProductId(pmsSkuInfo.getSpuId());
        skuService.saveSkuInfo(pmsSkuInfo);
        return "success";
    }
}
