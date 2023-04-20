package org.vipcube.spring.demo.service;

import org.vipcube.spring.demo.entity.AppUser;

import java.util.List;

public interface AppUserService {
    List<AppUser> findAll();

    AppUser getById(long id);

    AppUser create(AppUser appUser);
}
