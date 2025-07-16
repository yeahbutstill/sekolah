package com.hendisantika.sekolah.service.impl;

import com.hendisantika.sekolah.entity.Pengguna;
import com.hendisantika.sekolah.entity.Role;
import com.hendisantika.sekolah.repository.PenggunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PenggunaRepository penggunaRepository;

    public List<GrantedAuthority> getAuthorities(Pengguna user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }

        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Pengguna> user = penggunaRepository.findByUsername(username);

        if (user.isPresent()) {
            return new User(user.get().getUsername(),
                    user.get().getPassword(), getAuthorities(user.get()));
        } else {
            throw new UsernameNotFoundException("Invalid user tried to login. User not found exception");
        }
    }
}