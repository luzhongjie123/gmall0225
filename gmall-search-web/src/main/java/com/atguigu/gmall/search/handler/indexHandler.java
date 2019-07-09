package com.atguigu.gmall.search.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class indexHandler {
    @RequestMapping("index")
    public String toIndex(){
        return "index";
    }
}
