package com.promote.service;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IserviceRouter implements InitializingBean {
    private static final Map<Integer,Iservice> mapRouter = Maps.newHashMap();
    @Autowired
    private List<Iservice> iserviceList;
    @Override
    public void afterPropertiesSet() throws Exception {
        for (Iservice service : iserviceList){
            mapRouter.put(service.getType(),service);
        }
    }
    public Iservice getService(Integer type){
        return mapRouter.get(type);
    }
}
