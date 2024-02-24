package com.profcut.ordermanager.security.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "om_users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OmUserEntity implements UserDetails {
    /**
     * Идентификатор пользователя.
     */
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private UUID id;
    /**
     * Имя пользователя.
     */
    private String firstName;
    /**
     * Фамилия пользователя.
     */
    private String lastName;
    /**
     * Дата рождения пользователя.
     */
    private Date birthday;
    /**
     * e-mail/логин пользователя.
     */
    private String email;
    /**
     * Телефон пользователя.
     */
    private String phone;
    /**
     * Пароль пользователя.
     */
    private String password;
    /**
     * Признак блокировки пользователя.
     */
    private boolean isBlock;
    /**
     * Признак удаления пользователя.
     */
    private boolean isDeleted;

    @ManyToMany
    @JoinTable(name = "om_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<OmRoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(OmRoleEntity::getRole)
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
