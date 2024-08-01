package org.rdutta.jwt_access_demo.entity;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "JWT_USERS")
public class Users implements UserDetails{
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private long userId;
    @Column(name = "USERNAME",nullable = false, unique = true, updatable = true ,length = 150)
    private String username;
    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;
    @Column(name = "PASSWORD", nullable = false, unique = true, length = 255)
    private String password;
    @CreationTimestamp
    @Column(name = "CREATED_AT")
    private Date userCreatedAt;
    @UpdateTimestamp
    @Column(name = "MODIFIED_AT")
    private Date userModifiedAt;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
    

       public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
