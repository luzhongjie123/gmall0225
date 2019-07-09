package com.atguigu.gmall.cart.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.entity.OmsCartItem;
import com.atguigu.gmall.entity.PmsSkuInfo;
import com.atguigu.gmall.service.api.CartService;
import com.atguigu.gmall.service.api.SkuService;
import com.atguigu.gmall.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CartHandler {

    @Reference
    private SkuService skuService;

    @Reference
    private CartService cartService;
    @RequestMapping("addToCart")
    public String addToCart(OmsCartItem omsCartItem, HttpServletRequest request, HttpServletResponse response){
        String skuId = omsCartItem.getProductSkuId();
        PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId);//根据当前skuid查询数据库记录
        List<OmsCartItem> omsCartItems = new ArrayList<>();//装商品的list集合
        omsCartItem.setPrice(pmsSkuInfo.getPrice());
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setIsChecked("1");
        omsCartItem.setProductCategoryId(pmsSkuInfo.getCatalog3Id());
        omsCartItem.setProductId(pmsSkuInfo.getProductId());
        omsCartItem.setProductName(pmsSkuInfo.getSkuName());
        omsCartItem.setProductSkuId(pmsSkuInfo.getId());
        omsCartItem.setProductPic(pmsSkuInfo.getSkuDefaultImg());
        String memberId="1";
        //判断用户是否登陆
        if(StringUtils.isBlank(memberId)){
            //用户未登陆,使用cookie保存购物车
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            //判断cartListCookie是否为空
            if(StringUtils.isBlank(cartListCookie)){
                //为空,直接添加购物车
                omsCartItems.add(omsCartItem);
            }else{
                //不为空,判断当前添加的购物车在已有的购物车中是否重复
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
                //更新或者添加购物车
                boolean b=if_new_cart(omsCartItems,omsCartItem);
                if(b){
                    //新车
                    omsCartItems.add(omsCartItem);
                }else{
                    //旧车
                    for (OmsCartItem cartItem : omsCartItems) {
                        if(cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())){
                            cartItem.setQuantity(cartItem.getQuantity().add(omsCartItem.getQuantity()));
                            cartItem.setTotalPrice(cartItem.getQuantity().multiply(cartItem.getPrice()));
                            break;
                        }
                    }
                }

            }
            //重新保存到cookie中
            String omsCartItemsJSON = JSON.toJSONString(omsCartItems);
            CookieUtil.setCookie(request,response,"cartListCookie",omsCartItemsJSON,1000*60*60*24,true);
        }else{
            omsCartItem.setMemberId(memberId);
            omsCartItem.setMemberNickname("windir");
            //用户登陆,使用数据库保存购物车
           OmsCartItem omsCartItemDB= cartService.isCartExist(omsCartItem.getProductSkuId(),memberId);
           if(omsCartItemDB!=null){
               System.out.println("更新");
               omsCartItemDB.setQuantity(omsCartItemDB.getQuantity().add(omsCartItem.getQuantity()));
               cartService.editOmsCartItem(omsCartItemDB);
           }else{
               System.out.println("添加");
               cartService.addOmsCartItem(omsCartItem);
           }

        }
        return "redirect:success.html";
    }

    private boolean if_new_cart(List<OmsCartItem> omsCartItems, OmsCartItem omsCartItem) {
        boolean b=true;
        for (OmsCartItem cartItem : omsCartItems) {
            if(cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())){
                b=false;
                break;
            }
        }
        return b;
    }
}
