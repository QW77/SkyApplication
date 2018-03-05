package com.sky.app.bean;

import java.io.Serializable;

/**
 * Created by hongbang on 2017/5/12.
 */

public class SellUpload implements Serializable {

    String id_card_positive;
    String id_card_reverse;
    String business_license;

    public String getId_card_positive() {
        return id_card_positive;
    }

    public void setId_card_positive(String id_card_positive) {
        this.id_card_positive = id_card_positive;
    }

    public String getId_card_reverse() {
        return id_card_reverse;
    }

    public void setId_card_reverse(String id_card_reverse) {
        this.id_card_reverse = id_card_reverse;
    }

    public String getBusiness_license() {
        return business_license;
    }

    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }
}
