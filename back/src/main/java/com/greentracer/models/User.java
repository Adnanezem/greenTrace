package com.greentracer.models;

/**
 * Java representation of User data row.
 */
public class User {
    private String login;
    private String password;
    private String lname;
    private String fname;

    public User() {
    }

    public User(String login, String password, String lname, String fname) {
        this.login = login;
        this.password = password;
        this.lname = lname;
        this.fname = fname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    @Override
    public String toString() {
        return "User [login=" + login + ", password=" + password + ", lname=" + lname + ", fname=" + fname + "]";
    }

}
