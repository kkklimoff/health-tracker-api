package com.kkkneecapping.healthtrackerapi.security;

import com.kkkneecapping.healthtrackerapi.entity.User;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class SecurityUser implements UserDetails {

    private final User user;

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public UUID getPublicId() {
        return user.getPublicId();
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public @NonNull String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !Objects.equals(User.UserStatus.BLOCKED, user.getStatus());
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(User.UserStatus.ACTIVE, user.getStatus());
    }
}
