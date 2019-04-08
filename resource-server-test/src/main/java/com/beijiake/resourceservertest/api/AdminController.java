package com.beijiake.resourceservertest.api;

import com.beijiake.resourceservertest.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/do")
    public String doGet() {

        accountRepository.findById(1L).orElseThrow(EntityNotFoundException::new);

        return "administrator works,hh!";
    }
}
