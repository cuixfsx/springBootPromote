package com.promote.service.impl;


import com.promote.service.Iservice;
import org.springframework.stereotype.Service;

@Service
public class IserviceAImpl implements Iservice {
    @Override
    public Integer getType() {
        this.print("IserviceAImpl");
        return new Integer(1);
    }

    @Override
    public void print(String str) {
        System.out.println(str);
    }
}
