package com.grippaweb.jwt.services;

import java.net.URISyntaxException;

import com.grippaweb.jwt.dtos.User;

public interface IUserService {
    User loadUser(String uid) throws URISyntaxException;
}
