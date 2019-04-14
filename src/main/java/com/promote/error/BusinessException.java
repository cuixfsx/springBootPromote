package com.promote.error;

/**
 * 包装器设计模式
 */
public class BusinessException extends  Exception implements CommonError {

    private CommonError commonError;
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }
    public BusinessException(CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }
    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.setErrMsg(errMsg);
        return this;
    }
}
