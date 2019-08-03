package com.promote.config;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class JenkinsConfiguration {

    /*为jenkinsProperties注入配置值，也可以在jenkinsProperties上添加Component注解和ConfigurationProperties(prefix="project.jenkins")注解来实现注入功能*/
    @Bean(name = "jenkinsProperties")
    public JenkinsProperties jenkinsProperties(
            @Value("${project.jenkins.server-uri}") String serverUri,
            @Value("${project.jenkins.username}") String username,
            @Value("${project.jenkins.password}") String password) {
        JenkinsProperties jenkinsProperties = new JenkinsProperties();
        jenkinsProperties.setServerUri(serverUri);
        jenkinsProperties.setUsername(username);
        jenkinsProperties.setPassword(password);
        return jenkinsProperties;
    }

    /*注入jenkinsHttpClient对象*/
    @Bean(name = "jenkinsHttpClient")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public JenkinsHttpClient getJenkinsHttpClient(@Qualifier("jenkinsProperties") JenkinsProperties jenkinsProperties) throws URISyntaxException {
        return new JenkinsHttpClient(
                new URI(jenkinsProperties.getServerUri()),
                jenkinsProperties.getUsername(),
                jenkinsProperties.getPassword());
    }

    /*注入jenkinsServer对象*/
    @Bean(name = "jenkinsServer")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public JenkinsServer getJenkinsServer(@Qualifier("jenkinsHttpClient") JenkinsHttpClient jenkinsHttpClient) {
        return new JenkinsServer(jenkinsHttpClient);
    }
}