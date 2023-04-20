package org.vipcube.spring.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vipcube.spring.demo.entity.AppUser;
import org.vipcube.spring.demo.service.AppUserService;

@RestController
@RequestMapping("/api/app-user")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(this.appUserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        return ResponseEntity.ok(this.appUserService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AppUser appUser){
        return ResponseEntity.ok(this.appUserService.create(appUser));
    }
}
