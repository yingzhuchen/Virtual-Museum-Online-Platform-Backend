package org.openapitools.exception;

import org.springframework.http.HttpStatus;

//无权限
public class ExpectedException403 extends ExpectedException{
    public ExpectedException403(String message){
        super(message);
        this.statusCode= HttpStatus.FORBIDDEN;
    }
}
