package com.jian.family.business.user.entity;

import com.jian.family.config.security.constant.Authority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Getter
@Setter
@Entity
@Comment("用户信息表")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Comment("用户名")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Comment("姓名")
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Comment("手机号")
    @Column(name = "phone_number", unique = true, nullable = false, length = 11)
    private String phoneNumber;

    @Comment("密码")
    @Column(name = "password", nullable = false)
    private String password;

    @Comment("是否可用")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Override
    public List<Authority> getAuthorities() {
        return List.of(Authority.NORMAL, Authority.ADMIN, Authority.API);
    }
}
