package com.backend.users.User.Domain;

import com.backend.users.Adress.Domain.Adress;
import com.backend.users.Confirmation.Confirmation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private Boolean isActive;


    @OneToOne(cascade = CascadeType.ALL)
    private Confirmation confirmation;

    @OneToOne(cascade = CascadeType.ALL)
    private Adress adress;
    @Column(nullable = false)
    private String profilePicture;

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public  boolean isEnabled(){
        return isActive;
    }

    @Override
    public Collection< ? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


}
