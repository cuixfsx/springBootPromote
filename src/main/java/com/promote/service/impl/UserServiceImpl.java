package com.promote.service.impl;

import com.promote.controller.UserController;
import com.promote.controller.itemview.UserVO;
import com.promote.dao.UserDOMapper;
import com.promote.dao.UserPasswordDOMapper;
import com.promote.dataobject.UserDO;
import com.promote.dataobject.UserPasswordDO;
import com.promote.error.BusinessException;
import com.promote.error.EmBusinessError;
import com.promote.service.UserService;
import com.promote.service.model.UserModel;
import com.promote.validater.ValidationResult;
import com.promote.validater.ValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import javax.validation.Validation;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private ValidatorImpl validator;

    @Override
    public void register(UserModel userModel) throws BusinessException {

        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrorMgs());
        }
    }

    @Override
    public UserModel getUserById(Integer id) {

        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return converFromDataObject(userDO, userPasswordDO);

    }

    @Override
    @Async
    public void testAsync() {
        try {
            Thread.sleep(10*1000);
            logger.info("testAsync");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private UserModel converFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if (userPasswordDO == null) {
            return null;
        }
        userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        return userModel;
    }
}
