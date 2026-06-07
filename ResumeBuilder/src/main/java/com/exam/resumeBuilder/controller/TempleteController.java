package com.exam.resumeBuilder.controller;

import com.exam.resumeBuilder.Service.TempleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.exam.resumeBuilder.util.AppConstans.TEMPLATE;

@RestController
@RequiredArgsConstructor
@RequestMapping(TEMPLATE)
@Slf4j
public class TempleteController {

    private final TempleteService templeteService;

    @GetMapping
    public ResponseEntity<?> getTempletes(Authentication authentication){

        Map<String,Object> response =
                templeteService.getTempletes(authentication.getPrincipal());

        return ResponseEntity.ok(response);
    }
}