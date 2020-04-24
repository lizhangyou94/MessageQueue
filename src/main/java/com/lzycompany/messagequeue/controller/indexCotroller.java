package com.lzycompany.messagequeue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * * @decscription:
 * * @author:lzy
 * * @date:2019-05-30 11:25
 **/
@Controller
public class indexCotroller {

    @RequestMapping("/index/{param}")
    public String index(@PathVariable String param) {
        System.err.println("=======" + param + "==========");
        return "index";
    }
}
