package org.vipcube.spring.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vipcube.spring.demo.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
