package com.rezztoran.rezztoranbe.service.impl;

import com.rezztoran.rezztoranbe.model.User;
import com.rezztoran.rezztoranbe.service.impl.UserServiceImpl;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** The type User details service. */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserServiceImpl userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findUserByUsername(username);
    var roles =
        Stream.of(user.getRole())
            .map(x -> new SimpleGrantedAuthority(x.name()))
            .collect(Collectors.toList());
    return new org.springframework.security.core.userdetails.User(
        username, user.getPassword(), roles);
  }
}
