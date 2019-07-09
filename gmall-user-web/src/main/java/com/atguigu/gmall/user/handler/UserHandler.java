package com.atguigu.gmall.user.handler;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.service.api.UmsUserService;
import com.atguigu.gmall.entity.UmsMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserHandler {
    @Reference
    private UmsUserService umsUserService;

    @ResponseBody
    @RequestMapping("get/all")
    public  List<UmsMember> getALL(){
        List<UmsMember>  userList= umsUserService.getAll();
        return  userList;
    }



    @ResponseBody
    @RequestMapping("get/user/by/{uid}")
    public  UmsMember getUserById(@PathVariable("uid") String uid){
        UmsMember umsMember= umsUserService.getUserById(uid);
        return  umsMember;
    }

    @ResponseBody
    @RequestMapping("update/user/by/{uid}")
    public int updateMemberById(@PathVariable("uid") String uid, @RequestParam("userName")String userName){
        int result= umsUserService.updateMemberById(uid,userName);
        return  result;
    }
}
