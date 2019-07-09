package com.atguigu.gmall.service.api;

import com.atguigu.gmall.entity.OmsCartItem;

public interface CartService {

    OmsCartItem isCartExist(String productSkuId, String memberId);

    void editOmsCartItem(OmsCartItem omsCartItem1);

    void addOmsCartItem(OmsCartItem omsCartItem);
}
