package org.openapitools.exception;

import org.springframework.http.HttpStatus;

public class ExpectedException410 extends ExpectedException{
    //接口废弃
    public ExpectedException410(String message){
        super(message);
        this.statusCode= HttpStatus.GONE;
    }
}
