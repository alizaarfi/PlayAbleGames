package com.example.domain;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.serde.annotation.Serdeable;

@MappedEntity("users")
@Serdeable

public class User {
    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    @MappedProperty("UserID")
    private Long userID;

    @MappedProperty("Username")
    private String username;

    @MappedProperty("Email")
    private String email;

    @MappedProperty("EncryptedPassword")
    private String password;

    @MappedProperty("Enabled")
    private Boolean enabled = true;

    @MappedProperty("ProfilePictureURL")
    private String profilePictureURL;

    // getters & setters
    //userid
    public Long getUserID() {return userID;}
    public void setUserID(Long userID) {this.userID = userID;}

    //username
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    //email
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    //password
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    //enabled
    public Boolean getEnabled() {return enabled;}
    public void setEnabled(Boolean enabled) {this.enabled = enabled;}

    //profilepictureurl
    public String getProfilePictureURL() {return profilePictureURL;}
    public void setProfilePictureURL(String profilePictureURL) {this.profilePictureURL = profilePictureURL;}
}
