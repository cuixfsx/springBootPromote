package com.promote.service;

import com.promote.error.BusinessException;
import com.promote.service.model.UserModel;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {

    void register(UserModel userModel) throws BusinessException;

    UserModel getUserById(Integer id);

    void testAsync();
}
