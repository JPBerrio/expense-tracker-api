package com.API_rest.expense_tracker.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", length = 20)
    private Long idUser;

    @Column(nullable = false, name = "username", length = 30, unique = true)
    private String username;

    @Column(nullable = false, name = "last_name", length = 30)
    private String lastName;

    @Column(nullable = false, name = "password", length = 60)
    private String password;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "salary", columnDefinition = "Decimal(10,2)")
    private Double salary;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean locked;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean disabled;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<ExpenseEntity> expenses;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserRoleEntity> roles;
}
