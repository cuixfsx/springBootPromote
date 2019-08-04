package com.promote.controller;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.promote.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/jenkins")
public class JenkinsController {

    private Logger logger= LoggerFactory.getLogger(JenkinsController.class);

    @Autowired
    private JenkinsServer jenkinsServer;

    @GetMapping("/build")
    @ResponseBody
    private CommonReturnType buildProject(@RequestParam(name = "saveDir") String saveDir) throws IOException {
        try {
            JobWithDetails job = jenkinsServer.getJob("build-ci");
            job.getNextBuildNumber();    /*获取下一次构建的构建编号，可以用于在触发构建前，先记录构建编号。在后续获取指定编号的构建结果*/
            Map<String,String> map =  new HashMap<>();
            map.put("saveDir",saveDir);
            job.build(map);                 /*执行指定任务的构建操作*/
            Build build = job.getBuildByNumber(1);  /*获取某任务第一次构建的构建对象*/
            BuildWithDetails buildWithDetails = build.details(); /*子类转型*/
            String logs = buildWithDetails.getConsoleOutputText();
            logger.info(logs);
            return CommonReturnType.create("OK");
            dasdasd
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonReturnType.create("ERROR");

    }
}