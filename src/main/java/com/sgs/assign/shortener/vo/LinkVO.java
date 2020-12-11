package com.sgs.assign.shortener.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class LinkVO implements Serializable {

    String url;
    String encoded;
    int code;
    String message;

    public LinkVO(String url, String encoded) {
        this.url = url;
        this.encoded = encoded;
    }

    public void setCodeAndMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }


}
