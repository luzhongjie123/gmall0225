package com.atguigu.gmall.manage.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.entity.PmsProductImage;
import com.atguigu.gmall.entity.PmsProductInfo;
import com.atguigu.gmall.entity.PmsProductSaleAttr;
import com.atguigu.gmall.manage.util.MyUploadUtil;
import com.atguigu.gmall.service.api.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@Controller
public class SpuHandler {
    @Reference
    private SpuService spuService;

    @ResponseBody
    @RequestMapping("spuList")
    public List<PmsProductInfo> getProductByCatalog3Id(String catalog3Id){
        return spuService.getProductByCatalog3Id(catalog3Id);
    }

    @ResponseBody
    @RequestMapping("fileUpload")
    public String fileUpload(@RequestParam("file")MultipartFile file){
        String imgUrl= MyUploadUtil.uploadImg(file);
        return  imgUrl;
    }

    @ResponseBody
    @RequestMapping("saveSpuInfo")
    public String saveSpuInfo(@RequestBody  PmsProductInfo pmsProductInfo){
        spuService.saveSpuInfo(pmsProductInfo);
        return  "success";
    }


    @ResponseBody
    @RequestMapping("spuSaleAttrList")
    public  List<PmsProductSaleAttr> spuSaleAttrList(String spuId){
        List<PmsProductSaleAttr> productInfos=spuService.getSaleAttrBySpuId(spuId);
        return  productInfos;
    }

    @ResponseBody
    @RequestMapping("spuImageList")
    public List<PmsProductImage> spuImageList(String spuId){
        List<PmsProductImage>  productImages=spuService.getProductImageBySpuid(spuId);
        return  productImages;
    }
}
