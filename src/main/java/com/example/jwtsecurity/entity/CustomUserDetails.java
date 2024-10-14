package com.example.jwtsecurity.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Здесь можно вернуть роли пользователя, если они есть
        return null; // Если у тебя нет ролей, верни пустой список или null
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Используем пароль из сущности `User`
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // Используем имя пользователя из сущности `User`
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Здесь можно добавить логику для проверки истечения учетной записи
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Логика блокировки учетной записи
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Логика проверки истечения пароля
    }

    @Override
    public boolean isEnabled() {
        return true; // Логика активности учетной записи
    }
}
