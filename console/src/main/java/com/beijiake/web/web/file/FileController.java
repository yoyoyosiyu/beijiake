package com.beijiake.web.web.file;

import com.beijiake.web.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")

public class FileController {

    Logger logger = LoggerFactory.getLogger(getClass());



    @Autowired
    StorageService storageService;

    @PostMapping
    public ResponseEntity doPostFile(@RequestParam("File") MultipartFile file){



        storageService.store(file);


        return new ResponseEntity(HttpStatus.OK);


    }

}
