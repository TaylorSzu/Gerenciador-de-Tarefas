package com.saep.taylorsz.Service;

import com.saep.taylorsz.DTO.UserDTO;
import com.saep.taylorsz.IRepository.UserIRepository;
import com.saep.taylorsz.Models.User;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    private UserIRepository userIRepository;

    public UserService(UserIRepository userIRepository) {
        this.userIRepository = userIRepository;
    }

    public User save(User user) {
        try {
            if (user == null) {
                System.out.println("Erro: O objeto User está nulo");
                return null;
            }
            return userIRepository.create(user);
        } catch (DataAccessException e) {
            System.out.println("Erro de banco de dados: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
        return null;
    }

    public List<UserDTO> listAll() {
        List<UserDTO> users = new ArrayList<>();
        try {
            users = userIRepository.findAll();
        } catch (DataAccessException e) {
            System.out.println("Erro de banco ao buscar usuários: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao listar usuários: " + e.getMessage());
        }
        return users;
    }
}
