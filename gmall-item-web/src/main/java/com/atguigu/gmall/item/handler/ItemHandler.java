package com.atguigu.gmall.item.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.entity.PmsProductSaleAttr;
import com.atguigu.gmall.entity.PmsSkuInfo;
import com.atguigu.gmall.entity.PmsSkuSaleAttrValue;
import com.atguigu.gmall.service.api.SkuService;
import com.atguigu.gmall.service.api.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class ItemHandler {
    @Reference
    private SkuService skuService;

    @Reference
    private SpuService spuService;

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable("skuId")String skuId, Model model){
        PmsSkuInfo skuInfo= skuService.getSkuById(skuId);
        String productId=skuInfo.getProductId();
        List<PmsProductSaleAttr> saleAttrBySpuId = skuService.getspuSaleAttrListCheckBySku(productId,skuId);
        model.addAttribute("skuInfo",skuInfo);
        model.addAttribute("spuSaleAttrListCheckBySku",saleAttrBySpuId);

        List<PmsSkuInfo> pmsSkuInfos= skuService.getSkuInfoListBySpuId(productId);
        ArrayList<String> skuIds = new ArrayList<>();
        HashMap<String, String> skuSaleAttrMap = new HashMap<>();
        String attrValueId="";
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            String pmsSkuId = pmsSkuInfo.getId();
            List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
                if(pmsSkuId.equals(pmsSkuSaleAttrValue.getSkuId())){
                    attrValueId=attrValueId+pmsSkuSaleAttrValue.getSaleAttrValueId()+"|";
                }
            }
            skuSaleAttrMap.put(attrValueId,pmsSkuId);
            attrValueId="";
        }
        String skuSaleAttrJson = JSON.toJSONString(skuSaleAttrMap);
        model.addAttribute("skuSaleAttrMap",skuSaleAttrJson);


        return "item";
    }
}
