package com.promote.controller;

import com.promote.controller.itemview.UserVO;
import com.promote.error.BusinessException;
import com.promote.error.EmBusinessError;
import com.promote.response.CommonReturnType;
import com.promote.service.UserService;
import com.promote.service.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private HttpServletRequest httpServletRequest;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

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
        Random random = new Random();
        int randomInt = random.nextInt(9999);
        randomInt += 1000;
        String optCode = String.valueOf(randomInt);
        httpServletRequest.getSession().setAttribute("optCode",optCode);
        System.out.println("telephone "+ telephone + " optCode "+ optCode);
        userService.testAsync();
        for (int i = 0; i< 0; i++){
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

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
