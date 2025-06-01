package com.joseph.finance.application.services;

import com.joseph.finance.adapters.outbound.mappers.UserMapper;
import com.joseph.finance.application.ports.out.UsersRepositoryPort;
import com.joseph.finance.shared.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsServiceImplements implements UserDetailsService {

    @Autowired
    private UsersRepositoryPort _usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, BadRequestException {
        return _usersRepository.findByEmail(email).map(UserMapper::toEntity).orElseThrow(
            () -> new BadRequestException("Não foi possível validar o usuário")
        );
    }
}