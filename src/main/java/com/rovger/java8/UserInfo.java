package com.rovger.java8;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2021年08月26日 16:29
 */
public class UserInfo {

    private Long id;
    private Boolean isPaid;

    public UserInfo(Long id, Boolean isPaid) {
        this.id = id;
        this.isPaid = isPaid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
