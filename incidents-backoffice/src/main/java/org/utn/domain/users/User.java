package org.utn.domain.users;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public String email;
    public String password;
    public User(
        String email,
        String password
    ) {
        this.email = email;
        this.password = password;
    }

    protected User() {
        super();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}