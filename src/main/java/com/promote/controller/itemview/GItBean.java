package com.promote.controller.itemview;

import javax.validation.constraints.NotNull;

public class GItBean {

    @NotNull(message = "opened参数不能为空")
    private Boolean opened;

    public Boolean getOpened() {
        return opened;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;
    }
}
