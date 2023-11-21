package com.codelab.BytePassword.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BytePwd {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)

    private Long id;
    private String password;
    private String email;
    private String name;
    private String hint;
    private String message;
    private String logo;
    private String timestamp;

}
