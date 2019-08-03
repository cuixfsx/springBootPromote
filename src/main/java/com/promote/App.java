package com.promote;

import com.promote.config.TaskExecutorConfig;
import com.promote.service.GitHookBiz;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.promote.dao")
@EnableAsync
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
//        GitHookBiz asyncService = context.getBean(GitHookBiz.class);
//
//        for(int i = 0; i<10; i++)
//        {
//            asyncService.executorAsyncTask(i);
//        }
//
//        context.close();

        SpringApplication.run(App.class,args);
    }
}
