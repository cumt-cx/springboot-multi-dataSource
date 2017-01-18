package com.lovepi.sandwich;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by cumt_cx on 2017/1/18.
 */
@SpringBootApplication
@ServletComponentScan
public class Application {

    public static void main(String args[]){

        SpringApplication.run(Application.class,args);

    }
}
