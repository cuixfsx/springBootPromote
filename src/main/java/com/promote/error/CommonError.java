package com.promote.error;

public interface CommonError {
    int getErrorCode();

    String getErrMsg();

    CommonError setErrMsg(String errMsg);
}
