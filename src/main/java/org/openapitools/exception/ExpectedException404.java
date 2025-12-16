package org.openapitools.exception;

import org.springframework.http.HttpStatus;

//不存在
public class ExpectedException404 extends ExpectedException{
    public ExpectedException404(String message){
        super(message);
        this.statusCode= HttpStatus.NOT_FOUND;
    }
}
