package com.lib.model;

import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @Column(name = "user_name")
    private String username;

    @OneToMany(mappedBy = "book")
    private List<BorrowedBooks> borrowedBooks;

    @OneToMany(mappedBy = "user")
    private List<WaitList> waitlist;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return StringUtils.equalsIgnoreCase(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
