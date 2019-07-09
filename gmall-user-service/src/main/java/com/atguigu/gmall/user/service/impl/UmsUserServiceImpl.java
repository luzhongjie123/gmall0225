package com.atguigu.gmall.user.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.entity.UmsMember;

import com.atguigu.gmall.service.api.UmsUserService;

import com.atguigu.gmall.user.mapper.UmsMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UmsUserServiceImpl implements UmsUserService {
    @Autowired
    private UmsMemberMapper umsMemberMapper;

    @Override
    public List<UmsMember> getAll() {
        return umsMemberMapper.getAllUser();
    }


    @Override
    public UmsMember getUserById(String uid) {
        UmsMember umsMember = new UmsMember();
        umsMember.setId(uid);
        return umsMemberMapper.selectOne(umsMember);
    }



    @Override
    public int updateMemberById(String MemberId,String userName) {
        UmsMember umsMember = new UmsMember();
        umsMember.setId(MemberId);
        umsMember.setUsername(userName);
        return umsMemberMapper.updateByPrimaryKeySelective(umsMember);
    }
}
