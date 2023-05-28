package com.example.eugene.Model;

public class Users {
    private String fullName;
    private String email;
    private String password;
    int branch;

    public Users() {
    }

    public Users(String fullName, String email, String password, int branch) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.branch = branch;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }
}
