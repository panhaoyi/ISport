package com.tcl.isport.bean;

/**
 * Created by user on 17-9-4.
 */
public class User {
    private Integer _id;
    private String phonenumber;
    private String password;

    public User() {
    }

    public User(Integer _id, String phonenumber, String password) {
        this._id = _id;
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
