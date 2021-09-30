package com.example.thriftuniversecrud;

public class UserInformation {
    public String email;
    public String password;
    public String userFullName;
    public String  userPhone;
    public String  userAddress;
    public String  userPostalCode;

    public UserInformation(){
    }

    public UserInformation( String userFullName, String userPhone, String userAddress, String userPostalCode) {
        this.userFullName = userFullName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userPostalCode = userPostalCode;
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

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }


    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserPostalCode(String userPostalCode) {
        this.userPostalCode = userPostalCode;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserPostalCode() {
        return userPostalCode;
    }
}
