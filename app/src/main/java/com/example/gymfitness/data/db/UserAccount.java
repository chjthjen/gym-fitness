package com.example.gymfitness.data.db;

public class UserAccount {
    private String user_id;
    private String user_fullname;
    private String user_email;
    private String user_phone;
    private String user_password;
    private int isNormalUser;

    public UserAccount() {
        // Default constructor required for calls to DataSnapshot.getValue(UserAccount.class)
    }

    public UserAccount(String email, String password, String fullName) {
        this.user_email = email;
        this.user_password = password;
        this.user_fullname = fullName;
    }
    public UserAccount(String user_id, String user_fullname, String user_email, String user_phone, String user_password, int isNormalUser) {
        this.user_id = user_id;
        this.user_fullname = user_fullname;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_password = user_password;
        this.isNormalUser = isNormalUser;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public int isNormalUser() {
        return isNormalUser;
    }

    public void setNormalUser(int normalUser) {
        isNormalUser = normalUser;
    }
}
