package com.promote.controller;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/jenkins")
public class JenkinsController {

    private Logger logger= LoggerFactory.getLogger(GitHookController.class);

    @Autowired
    private JenkinsServer jenkinsServer;

    @GetMapping("/build")
    private String buildProject() throws IOException {
        try {
            JobWithDetails job = jenkinsServer.getJob("build-ci");
            job.getNextBuildNumber();    /*获取下一次构建的构建编号，可以用于在触发构建前，先记录构建编号。在后续获取指定编号的构建结果*/
            job.build();                 /*执行指定任务的构建操作*/
            Build build = job.getBuildByNumber(1);  /*获取某任务第一次构建的构建对象*/
            BuildWithDetails buildWithDetails = build.details(); /*子类转型*/
            String logs = buildWithDetails.getConsoleOutputText();
            logger.info(logs);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
}