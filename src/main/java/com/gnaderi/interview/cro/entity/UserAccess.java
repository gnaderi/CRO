package com.gnaderi.interview.cro.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "USER_ACCESS")
public class UserAccess {
    private Long id;
    private User userByUserId;
    private Role roleByRoleId;

    public UserAccess() {
    }

    public UserAccess(User userByUserId, Role roleByRoleId) {
        this.userByUserId = userByUserId;
        this.roleByRoleId = roleByRoleId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccess userAccess = (UserAccess) o;
        return Objects.equals(id, userAccess.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    public Role getRoleByRoleId() {
        return roleByRoleId;
    }

    public void setRoleByRoleId(Role roleByRoleId) {
        this.roleByRoleId = roleByRoleId;
    }
}
