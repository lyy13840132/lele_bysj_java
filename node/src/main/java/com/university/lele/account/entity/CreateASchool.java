package com.university.lele.account.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "create_a_school")
public class CreateASchool implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学校的唯一标识
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    /**
     * 学校名称
     */
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "user_type", nullable = false)
    private int userType;

}
