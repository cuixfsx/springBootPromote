package com.promote.service;

import com.promote.error.BusinessException;
import com.promote.service.model.UserModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserService {

    void register(UserModel userModel) throws BusinessException;

    UserModel getUserById(Integer id);
    List<UserModel> getAll();

    void testAsync();
}
