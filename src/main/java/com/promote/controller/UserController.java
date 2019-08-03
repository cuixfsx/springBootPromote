package com.promote.controller;

import com.google.common.collect.Maps;
import com.promote.config.WebConfig;
import com.promote.controller.itemview.UserVO;
import com.promote.error.BusinessException;
import com.promote.error.EmBusinessError;
import com.promote.response.CommonReturnType;
import com.promote.service.Iservice;
import com.promote.service.IserviceRouter;
import com.promote.service.UserService;
import com.promote.service.model.UserModel;
import com.promote.strategy.ConcreteStrategyA;
import com.promote.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController{

    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("concreteStrategyA")
    private Strategy strategy;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private IserviceRouter iserviceRouter;
    @Autowired(required = false)
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private Map<String,Iservice> map;
    @Autowired
    @Qualifier("iserviceAImpl")
    private Iservice iservice1;
    @Resource(name = "iserviceBImpl")
    private Iservice iservice2;

    private static Map<String, Iservice> queryServiceMap = new HashMap<>();

    /**
     * @PostContruct是spring框架的注解 spring容器初始化的时候执行该方法
     */
    @PostConstruct
    private void init(){
        queryServiceMap.put("queryServiceOne", iservice1);
        queryServiceMap.put("queryServiceTwo", iservice2);
    }

    @RequestMapping("/test")
    @ResponseBody
    public CommonReturnType test(@RequestParam(name = "type") Integer type) throws BusinessException {
        Iservice iservice = iserviceRouter.getService(type);
        logger.info(queryServiceMap.get("queryServiceOne")+"");
        return CommonReturnType.create(map.get("iserviceAImpl").getType());

    }
    @RequestMapping("/register")
    @ResponseBody
    public CommonReturnType register(UserVO userVO) throws BusinessException {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userVO,userModel);
        userService.register(userModel);
        return CommonReturnType.create(null);

    }
    @RequestMapping(value = "/getopt",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getopt(@RequestParam(name = "telephone") String telephone) throws BusinessException {

        strategy.commonMethod1();
        strategy.algorithmInterface();
        Random random = new Random();
        int randomInt = random.nextInt(9999);
        randomInt += 1000;
        String optCode = String.valueOf(randomInt);
        httpServletRequest.getSession().setAttribute("optCode",optCode);
        System.out.println("telephone "+ telephone + " optCode "+ optCode);
        userService.testAsync();
        for (int i = 0; i< 10; i++){
            int index = i;
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        logger.info("thread "+ index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return CommonReturnType.create(null);
    }
    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {

        UserModel userModel = userService.getUserById(id);
        if (userModel == null){
            throw new BusinessException(EmBusinessError.UERT_NOT_EXIST);
        }

        return CommonReturnType.create(convertFromModel(userModel));

    }
    @RequestMapping("/getAll")
    @ResponseBody
    public CommonReturnType getAll() throws BusinessException {

        List<UserModel> userModel = userService.getAll();

        return CommonReturnType.create(userModel);

    }

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
