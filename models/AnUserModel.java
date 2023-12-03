package com.qlilop.kurs.models;

public class AnUserModel {
    public final int id;
    public final String login;
    public final String password;
    public final UserRole role;
    public final AssignedUsers assignedUsers;

    public AnUserModel(int id,
                       String login,
                       String password,
                       UserRole role,
                       AssignedUsers assignedUsers
    ) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.assignedUsers = assignedUsers;
    }

    public AnUserModel(int id, String login, String password, UserRole role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.assignedUsers = new AssignedUsers(id);
    }

    @Override
    public String toString() {
        return "AnUserModel{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", assignedUsers=" + assignedUsers +
                '}';
    }
}
