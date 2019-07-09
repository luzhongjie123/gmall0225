package com.atguigu.gmall.search.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.entity.*;
import com.atguigu.gmall.service.api.AttrInfoService;
import com.atguigu.gmall.service.api.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Controller
public class SearchHandler {
    @Reference
    private SearchService searchService;
    @Reference
    private AttrInfoService attrInfoService;

    @RequestMapping("list.html")
    public String toList(SkuParam skuParam, Model model) {
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = searchService.getPmsSearchSkuInfoBySkuParam(skuParam);
        Integer pageTotalCount = skuParam.getPageTotalCount();
        HashSet<String> skuAttrValueIds = new HashSet<>();

        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                String skuAttrValueId = pmsSkuAttrValue.getValueId();
                skuAttrValueIds.add(skuAttrValueId);
            }
        }

        List<PmsBaseAttrInfo> pmsBaseAttrInfos = null;
        if (skuAttrValueIds.size() > 0) {
            pmsBaseAttrInfos = attrInfoService.getAttrValueById(skuAttrValueIds);
            model.addAttribute("urlParam", getUrlParam(skuParam));
        }


        //减轻当前请求的平台属性和属性值,并添加面包屑
        String[] valueId = skuParam.getValueId();

        List<PmsSearchCrumb> attrValueSelectedList = new ArrayList<>();
       /* if(valueId!=null&&valueId.length>0){
            for (String crumbId : valueId) {
                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                pmsSearchCrumb.setValueName(crumbId);
                pmsSearchCrumb.setUrlParam(getUrlParam(skuParam,crumbId));
                attrValueSelectedList.add(pmsSearchCrumb);
            }
        }*/
        if (valueId != null && valueId.length > 0) {
            for (String id : valueId) {
                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                pmsSearchCrumb.setUrlParam(getUrlParam(skuParam, id));
                Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
                while (iterator.hasNext()) {
                    PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                    List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                    for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                        if (pmsBaseAttrValue.getId().equals(id)) {
                            pmsSearchCrumb.setValueName(pmsBaseAttrValue.getValueName());
                            iterator.remove();
                        }
                    }
                }
                attrValueSelectedList.add(pmsSearchCrumb);
            }
        }


        model.addAttribute("attrValueSelectedList", attrValueSelectedList);
        model.addAttribute("attrList", pmsBaseAttrInfos);
        model.addAttribute("skuLsInfoList", pmsSearchSkuInfos);
        SkuParam skuParam1 = new SkuParam();
        BeanUtils.copyProperties(skuParam,skuParam1);

        if(skuParam1.getPageTotalCount() % skuParam.getPageSize()==0){
            skuParam1.setPageTotal(skuParam.getPageTotalCount() % skuParam.getPageSize());
        }else{
            skuParam1.setPageTotal((skuParam.getPageTotalCount() % skuParam.getPageSize())+1);
        }


        model.addAttribute("skuParam",skuParam1);
        return "list";
    }

    private String getUrlParam(SkuParam skuParam, String... crumbUrlParam) {
        String urlParam = "";
        String catalog3Id = skuParam.getCatalog3Id();
        if (catalog3Id != null && catalog3Id.length() > 0) {
            /*if (StringUtils.isNotBlank(catalog3Id)) {
                urlParam = urlParam + "&";
            }*/
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }
        String keyword = skuParam.getKeyword();
        if (keyword != null && keyword.length() > 0) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        String[] valueId = skuParam.getValueId();
        if (valueId != null && valueId.length > 0) {
            for (String id : valueId) {
                if (crumbUrlParam.length == 0 || (crumbUrlParam.length > 0 && !id.equals(crumbUrlParam[0]))) {
                    urlParam = urlParam + "&valueId=" + id;
                }
            }
        }

        return urlParam;
    }
}
