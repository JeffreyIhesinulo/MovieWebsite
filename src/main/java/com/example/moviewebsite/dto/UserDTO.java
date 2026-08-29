package com.example.moviewebsite.dto;

public class UserDTO {
    Long id;
    String userName;
    String userEmail;

    public UserDTO(Long id, String userName, String userEmail)
    {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
    }

}
