package com.promote.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.promote.controller.itemview.GItBean;
import com.promote.controller.itemview.JsonData;
import com.promote.error.BusinessException;
import com.promote.error.EmBusinessError;
import com.promote.response.CommonReturnType;
import com.promote.service.GitHookBiz;
import com.promote.service.model.GitHookData;
import com.promote.validater.ValidationResult;
import com.promote.validater.ValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
@RequestMapping("/git")
@CrossOrigin
public class GitHookController extends BaseController {

    private Logger logger= LoggerFactory.getLogger(GitHookController.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GitHookBiz gitHookBiz;

    @RequestMapping("/sonar")
    public CommonReturnType sonar(@RequestHeader HttpHeaders httpHeaders,
                                     @RequestBody String requestBody) {
        logger.info("git "+ requestBody);
        return CommonReturnType.create(requestBody);
    }
    @RequestMapping("/push")
    public CommonReturnType register(@RequestHeader HttpHeaders httpHeaders,
                                     @RequestBody String requestBody) throws BusinessException {
        //System.out.println(requestBody);
        logger.info("git "+ requestBody);
        GitHookData gitHookData = JSON.parseObject(requestBody, new TypeReference<GitHookData>() {});
        return CommonReturnType.create(gitHookData);
    }

    @RequestMapping("/save")
    public CommonReturnType save(GItBean gItBean) throws BusinessException {
        gitHookBiz.save(gItBean);
        return CommonReturnType.create(gItBean);
    }
    @RequestMapping("/async")
    public CommonReturnType async(int num) {
        for(int i = 0; i<num; i++)
        {
            gitHookBiz.executorAsyncTask(i);
        }
        return CommonReturnType.create("");
    }

    @RequestMapping("/decimal")
    public CommonReturnType decimal() {
        BigDecimal bigDecimal = getBigDecimal();
        if (null == bigDecimal){
            bigDecimal = BigDecimal.ZERO;

        }
        return CommonReturnType.create(bigDecimal);
    }

    private BigDecimal getBigDecimal(){
        return null;
    }
    @RequestMapping("/rest")
    public JsonData rest() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String strbody=restTemplate.exchange("htp://localhost:8090/git/json", HttpMethod.GET, entity,String.class).getBody();
        JsonData jsonData= JSONObject.parseObject(strbody,JsonData.class);

        return jsonData;
    }
    @GetMapping("/json")
    public CommonReturnType json() {
        String json = "{\"headerInfo\":{\"state\":0,\"messageError\":\"\"},\"data\":\"[]\"}";
        JsonData jsonData = JSON.parseObject(json, new TypeReference<JsonData>() {});
        return CommonReturnType.create(jsonData);
    }

}
