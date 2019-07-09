package com.atguigu.gmall.search.service.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.entity.Page;
import com.atguigu.gmall.entity.PmsSearchSkuInfo;
import com.atguigu.gmall.entity.PmsSkuInfo;
import com.atguigu.gmall.entity.SkuParam;
import com.atguigu.gmall.service.api.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl  implements SearchService {
    @Autowired
    private JestClient jestClient;
    @Override
    public  List<PmsSearchSkuInfo> getPmsSearchSkuInfoBySkuParam(SkuParam skuParam) {
        ArrayList<PmsSearchSkuInfo> pmsSearchSkuInfos=null;
        try {
            //jest提供的工具类,这个类的值就是query下的字符串
            SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();

            BoolQueryBuilder boolQueryBuilder= new BoolQueryBuilder();
            if(skuParam.getKeyword()!=null){
                //分词查询
                MatchQueryBuilder matchQueryBuilder=new MatchQueryBuilder("skuName",skuParam.getKeyword());
                boolQueryBuilder.must(matchQueryBuilder);

                //高亮
                HighlightBuilder highlightBuilder=new HighlightBuilder();
                highlightBuilder.field("skuName");
                highlightBuilder.preTags("<span style='color:red'>");
                highlightBuilder.postTags("</span>");
                searchSourceBuilder.highlight(highlightBuilder);

            }
            if(skuParam.getCatalog3Id()!=null){
                //过滤条件
                TermQueryBuilder termQueryBuilder=new TermQueryBuilder("catalog3Id",skuParam.getCatalog3Id());
                boolQueryBuilder.filter(termQueryBuilder);
            }
            String[] values=skuParam.getValueId();
            if(values!=null&&values.length>0){
                //平台属性值不为空,当做过滤条件
                for (int i=0;i<values.length;i++){
                    String valueId=values[i];
                    TermQueryBuilder termQueryBuilder=new TermQueryBuilder("skuAttrValueList.valueId",valueId);
                    boolQueryBuilder.filter(termQueryBuilder);
                }
            }

           searchSourceBuilder.query(boolQueryBuilder);

            //分页
            int from =(skuParam.getPageNo()-1)*skuParam.getPageSize();
            searchSourceBuilder.from(from);
            searchSourceBuilder.size(skuParam.getPageSize());

            //指定在哪个index索引中查询
            Search search=new Search.Builder(searchSourceBuilder.toString()).addIndex("gmall").addType("PmsSearchSkuInfo").build();
            System.out.println(searchSourceBuilder.toString());
            //执行,返回结果
            SearchResult searchResult = jestClient.execute(search);
            Long total = searchResult.getTotal();
            System.out.println(total);
            skuParam.setPageTotalCount(Integer.parseInt(total.toString()));
            pmsSearchSkuInfos = new ArrayList<>();
            //从结果中得到hits对象,对象.source可以取出其中的值
            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);

            for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
                PmsSearchSkuInfo pmsSearchSkuInfo = hit.source;
                //获得高丽显示的skuName
                if(skuParam.getKeyword()!=null){
                    List<String> skuName = hit.highlight.get("skuName");
                    pmsSearchSkuInfo.setSkuName(skuName.get(0));
                }


                pmsSearchSkuInfos.add(pmsSearchSkuInfo);
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
        return pmsSearchSkuInfos;
    }
}
