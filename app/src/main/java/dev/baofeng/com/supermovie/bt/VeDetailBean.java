package dev.baofeng.com.supermovie.bt;

/*
 * 创建重点关注组条目的bean
 * <p>
 * created by song on 2017/5/17.14:27
 */

import java.io.Serializable;

public class VeDetailBean implements Serializable {
    public VeDetailBean(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    //牌照信息
    private String licensePlate;

}
