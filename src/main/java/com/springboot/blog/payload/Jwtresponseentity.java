package com.springboot.blog.payload;


public class Jwtresponseentity {

    private  String jwttoken;
    private String tokenType = "Bearer";

    public Jwtresponseentity(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setJwttoken(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
