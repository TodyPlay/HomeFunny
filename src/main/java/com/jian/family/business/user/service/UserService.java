package com.jian.family.business.user.service;

import com.jian.family.business.authentication.dto.RegisterRequest;
import com.jian.family.business.authentication.dto.RegisterResponse;
import com.jian.family.business.user.entity.UserEntity;
import com.jian.family.business.user.ex.UserExistException;
import com.jian.family.business.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterResponse registerUser(RegisterRequest request) {

        if (repository.findByUsername(request.username()).isPresent()) {
            throw new UserExistException();
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.username());
        user.setName(request.name());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(request.password()));
        var saved = repository.save(user);

        return new RegisterResponse(saved.getId(), saved.getUsername());
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<UserEntity> findById(Long aLong) {
        return repository.findById(aLong);
    }
}
