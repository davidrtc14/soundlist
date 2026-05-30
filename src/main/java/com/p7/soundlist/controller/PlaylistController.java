package com.p7.soundlist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @GetMapping
    public String helloWorld(){
        return "Hello World";
    }
}
