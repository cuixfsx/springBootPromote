package com.promote.controller.itemview;

import java.io.Serializable;

public class JsonData implements Serializable {


    /**
     * headerInfo : {"state":0,"messageError":""}
     * data : []
     */

    private HeaderInfoBean headerInfo;
    private String data;

    public HeaderInfoBean getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(HeaderInfoBean headerInfo) {
        this.headerInfo = headerInfo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static class HeaderInfoBean {
        /**
         * state : 0
         * messageError :
         */

        private int state;
        private String messageError;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getMessageError() {
            return messageError;
        }

        public void setMessageError(String messageError) {
            this.messageError = messageError;
        }
    }
}
