package com.atguigu.gmall.manage.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.entity.PmsBaseCatalog1;
import com.atguigu.gmall.entity.PmsBaseCatalog2;
import com.atguigu.gmall.entity.PmsBaseCatalog3;
import com.atguigu.gmall.service.api.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@CrossOrigin
@Controller
public class CatalogHandler {
    @Reference
    private CatalogService catalogService;

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return  "index";
    }



    @ResponseBody
    @RequestMapping("/getCatalog1")
    public List<PmsBaseCatalog1> getCatalog1s(){
        return catalogService.getPmsBaseCatalog1s();
    }

    @ResponseBody
    @RequestMapping("/getCatalog2")
    public List<PmsBaseCatalog2> getCatalog2s(String catalog1Id){
        return catalogService.getPmsBaseCatalog2s(catalog1Id);
    }
    @ResponseBody
    @RequestMapping("/getCatalog3")
    public List<PmsBaseCatalog3> getCatalog3s(String catalog2Id){
        return catalogService.getPmsBaseCatalog3s(catalog2Id);
    }


}
