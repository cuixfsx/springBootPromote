package com.promote.service.impl;

import com.promote.service.Iservice;
import org.springframework.stereotype.Service;

@Service
public class IserviceBImpl implements Iservice {
    @Override
    public Integer getType() {
        this.print("IserviceBImpl");
        return new Integer(2);
    }

    @Override
    public void print(String str) {
        System.out.println(str);
    }
}
