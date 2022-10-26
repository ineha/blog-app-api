package com.myproject.blog_app.Exception;

import com.myproject.blog_app.PayLoad.ApiResponse;
import com.sun.net.httpserver.Authenticator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
      String message = ex.getMessage();
      ApiResponse apiResponse=new ApiResponse(message,false);
        System.out.println("inside exceptio");
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
      //return new ResponseEntity<ApiResponse>(apiResp,onse, HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<Map<String,String>> methodrgumnetNotValidHandler(MethodArgumentNotValidException ex){

        HashMap<String,String> res= new HashMap<>();
        res.put(String.valueOf(ex.getParameter()), ex.getMessage());
        ex.getBindingResult().getAllErrors().forEach((errors)->{
             res.put(((FieldError) errors).getField(),errors.getDefaultMessage());
        });
        return new ResponseEntity<Map<String, String>>(res,HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
//        String message = ex.getMessage();
//        ApiResponse apiResponse=new ApiResponse(message,false);
//        System.out.println("inside exceptio");
//        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
//        //return new ResponseEntity<ApiResponse>(apiResp,onse, HttpStatus.NOT_FOUND);
//
//    }
}
