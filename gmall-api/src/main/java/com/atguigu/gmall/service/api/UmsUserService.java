package com.atguigu.gmall.service.api;


import com.atguigu.gmall.entity.UmsMember;

import java.util.List;

public interface UmsUserService {
    public List<UmsMember> getAll();




    UmsMember getUserById(String uid);


    int updateMemberById(String MemberId,String userName);
}
