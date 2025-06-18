package com.saep.taylorsz.IRepository;

import com.saep.taylorsz.DTO.UserDTO;
import com.saep.taylorsz.Models.User;

import java.util.List;

public interface UserIRepository { //tem formas melhores de fazer de maneira automatica mas vamos fazer manual mesmo
    User create(User user);
    List<UserDTO> findAll();
}
