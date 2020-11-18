package com.vertigo.web.dtos;

import com.vertigo.models.ErsUserRole;

import java.util.Collection;
import java.util.List;

public class ErsUserDTO {

    private String id;
    private String username;
    private String role;

    public ErsUserDTO() {
    }

    public ErsUserDTO(String id, String username, Collection<ErsUserRole> roles) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
