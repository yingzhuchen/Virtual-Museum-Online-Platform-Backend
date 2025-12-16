package org.openapitools.exception;

import org.springframework.http.HttpStatus;

public class ExpectedException400 extends ExpectedException{
    //参数出错
    public ExpectedException400(String message){
        super(message);
        this.statusCode= HttpStatus.BAD_REQUEST;
    }
}
