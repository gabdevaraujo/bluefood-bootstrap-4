package com.bs.bluefood.bs_bluefood.infrastructure.web.controller;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.bluefood.bs_bluefood.application.services.ImageService;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;
    
    @GetMapping(path = "/images/{type}/{imgName}", produces = org.springframework.http.MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getBytes(
        @PathVariable("type") String type,
        @PathVariable("imgName") String imgName){

        return imageService.getBytes(type, imgName);

    }
}