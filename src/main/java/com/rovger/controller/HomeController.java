package com.rovger.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 一定要注意：服务已经加入Zookeeper(port:8080)管理，所以，启动前需要启动Zookeeper为前提。。。
 * Created by weijlu on 2017/3/27.
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    Gson gson = new Gson();

    //ModelAndView
    @RequestMapping("/index")
    public ModelAndView index() {
        logger.info("The first jsp page.");
        return new ModelAndView("index");
    }

    //http://localhost:8080/views/home/env?env=prod
    @RequestMapping(value = "/{env}", method = RequestMethod.GET)
    public ModelAndView getEnv(@PathVariable(value = "env") String env) {
        ModelAndView view = new ModelAndView();
        ModelMap model = new ModelMap();
        model.put("currentEnv", env);
        view.setViewName("index");
        view.addObject("model", model);
        return view;
    }

    //API
    @RequestMapping(value = "/sayHello/{msg}", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    public @ResponseBody
    String hello2You(@PathVariable("msg") String msg) {
        logger.info("apis hello2You..." + msg);
        return "Hello " + msg;
    }

    @RequestMapping(value = "/info", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    public @ResponseBody
    String info() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "Rovger");
        map.put("age", "26");
        return gson.toJson(map);
    }

}
