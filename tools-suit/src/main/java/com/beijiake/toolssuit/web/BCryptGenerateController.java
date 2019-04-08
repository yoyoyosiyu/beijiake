package com.beijiake.toolssuit.web;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BCryptGenerateController {

    @RequestMapping("/bcrypt")
    public ModelAndView doGenerateBCryptEncode(
            HttpServletRequest httpServletRequest,
            ModelAndView modelAndView,
            @RequestParam(required = false) String password) {

        if (httpServletRequest.getMethod().equals("POST")) {


            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            modelAndView.addObject("encode", passwordEncoder.encode(password));


        }

        modelAndView.setViewName("bcrypt");


        return modelAndView;

    }

}
