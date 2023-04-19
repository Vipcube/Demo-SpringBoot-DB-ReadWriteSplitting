package org.vipcube.spring.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    private long id;
    private String name;
}
