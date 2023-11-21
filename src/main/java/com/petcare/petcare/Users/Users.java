package com.petcare.petcare.Users;

import java.util.ArrayList;
import java.util.List;

public class Users implements UsersInterface {
    private List<User> users;

    public Users(List<User> users) {
        this.users = users;
    }

    public Users() {
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return this.users;
    }
}
