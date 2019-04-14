package com.beijiake.web.web;


import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {


    @GetMapping("/login")
    public ModelAndView displayLoginPage(@RequestParam(name="error", required = false) String error) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("login");

        if (error != null)
            modelAndView.addObject("error", "Invalid user name or password");

        return modelAndView;
    }
}
