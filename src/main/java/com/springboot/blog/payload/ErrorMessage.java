package com.springboot.blog.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor

@Getter
public class ErrorMessage {

    private Date timestamp;
    private String message;
    private String details;



}
