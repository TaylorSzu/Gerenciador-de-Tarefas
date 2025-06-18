package com.saep.taylorsz.Service;

import com.saep.taylorsz.DTO.TaskDTO;
import com.saep.taylorsz.IRepository.TaskIRepository;
import com.saep.taylorsz.Models.Task;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {

    private final TaskIRepository taskIRepository;

    public TaskService(TaskIRepository taskIRepository) {
        this.taskIRepository = taskIRepository;
    }

    public Task save(Task task) {
        try {
            if (task == null) {
                System.out.println("Erro: O objeto Task está nulo");
                return null;
            }
            return taskIRepository.create(task);
        } catch (DataAccessException e) {
            System.out.println("Erro de banco de dados ao salvar tarefa: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao salvar tarefa: " + e.getMessage());
        }
        return null;
    }

    public List<TaskDTO> listAll() {
        List<TaskDTO> tasks = new ArrayList<>();
        try {
            tasks = taskIRepository.findAll();
        } catch (DataAccessException e) {
            System.out.println("Erro de banco ao buscar tarefas: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao listar tarefas: " + e.getMessage());
        }
        return tasks;
    }

    public Optional<TaskDTO> findById(Long id) {
        try {
            return taskIRepository.findBy(id);
        } catch (DataAccessException e) {
            System.out.println("Erro de banco ao buscar tarefa por id: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao buscar tarefa por id: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Task update(Task task) {
        try {
            if (task == null) {
                System.out.println("Erro: O objeto Task para atualização está nulo");
                return null;
            }
            return taskIRepository.update(task);
        } catch (DataAccessException e) {
            System.out.println("Erro de banco ao atualizar tarefa: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao atualizar tarefa: " + e.getMessage());
        }
        return null;
    }

    public TaskDTO updateStatus(int id, String status) {
        try {
            return taskIRepository.updateStatus(id, status);
        } catch (DataAccessException e) {
            System.out.println("Erro de banco ao atualizar status da tarefa: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao atualizar status da tarefa: " + e.getMessage());
        }
        return null;
    }

    public void delete(int id) {
        try {
            taskIRepository.delete(id);
        } catch (DataAccessException e) {
            System.out.println("Erro de banco ao deletar tarefa: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao deletar tarefa: " + e.getMessage());
        }
    }
}
