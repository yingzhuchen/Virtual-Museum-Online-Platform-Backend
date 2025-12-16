package org.openapitools.exception;

import org.springframework.http.HttpStatus;

public class ExpectedException extends Exception{
    protected HttpStatus statusCode;
    public ExpectedException(String message){
        super(message);
        this.statusCode=HttpStatus.INTERNAL_SERVER_ERROR;//500作为默认响应码
    }
    public ExpectedException(String message,int statusCode){
        super(message);
        this.statusCode=HttpStatus.valueOf(statusCode);
    }
    public HttpStatus getStatusCode(){
        return statusCode;
    }
}
