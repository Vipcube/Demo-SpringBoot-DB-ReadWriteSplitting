package org.vipcube.spring.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vipcube.spring.demo.entity.AppUser;
import org.vipcube.spring.demo.repository.AppUserRepository;
import org.vipcube.spring.demo.service.AppUserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository repository;

    @Override
    public List<AppUser> findAll() {
        return this.repository.findAll();
    }

    @Override
    public AppUser getById(long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public AppUser create(AppUser appUser) {
        return this.repository.save(appUser);
    }
}
