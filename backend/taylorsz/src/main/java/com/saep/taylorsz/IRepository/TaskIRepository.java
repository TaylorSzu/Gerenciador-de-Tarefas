package com.saep.taylorsz.IRepository;

import com.saep.taylorsz.DTO.TaskDTO;
import com.saep.taylorsz.Models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskIRepository { //tem formas melhores de fazer de maneira automatica mas vamos fazer manual mesmo
    Task create(Task task);
    Task update(Task task);
    TaskDTO updateStatus(int id, String status);
    List<TaskDTO> findAll();
    Optional<TaskDTO> findBy(Long id);
    void delete(int id);
}
