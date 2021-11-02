package com.diana.controller;

import com.diana.repository.DiagnoseRepository;
import com.diana.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/main"})
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiagnoseRepository diagnoseRepository;

    public MainController() {
    }

    @GetMapping({"/main"})
    public void home() {
    }
}

