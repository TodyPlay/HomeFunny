package com.jian.family.business.user.api;

import com.jian.family.business.user.dto.UserDto;
import com.jian.family.business.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("find-by-id")
    public Optional<UserDto> byId(Long id) {
        return userService.findById(id).map(e -> new UserDto(e.getId(), e.getName(), e.getUsername()));
    }
}
