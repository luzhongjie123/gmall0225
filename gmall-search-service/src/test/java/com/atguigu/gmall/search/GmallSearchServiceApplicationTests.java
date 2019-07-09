package com.atguigu.gmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.entity.PmsSearchSkuInfo;
import com.atguigu.gmall.entity.PmsSkuInfo;
import com.atguigu.gmall.service.api.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallSearchServiceApplicationTests {
    @Autowired
    private JestClient jestClient;

    @Reference
    private SkuService skuService;

    @Test
    public void contextLoads() {
        String query="";
        /*Search search=new Search.Builder("query:{\"match\": {\n" +
                "      \n" +
                "      \"name\":\"湄公河行动\"\n" +
                "    }}").addIndex("moive_chn").addType("moive").build();
        try {
             JestResult execute = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        List<PmsSearchSkuInfo> pmsSearchSkuInfos=new ArrayList<>();
        List<PmsSkuInfo> skuInfos=skuService.getSkuAll();
        for (PmsSkuInfo skuInfo : skuInfos) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            BeanUtils.copyProperties(skuInfo,pmsSearchSkuInfo);
            String skuId = skuInfo.getId();
            pmsSearchSkuInfo.setId(Long.parseLong(skuId));
            pmsSearchSkuInfos.add(pmsSearchSkuInfo);
        }
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            Index index = new Index.Builder(pmsSearchSkuInfo).index("gmall").type("PmsSearchSkuInfo").id(pmsSearchSkuInfo.getId()+"").build();
            try {
                jestClient.execute(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
