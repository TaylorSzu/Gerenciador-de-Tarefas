package com.saep.taylorsz.Repository;

import com.saep.taylorsz.DTO.TaskDTO;
import com.saep.taylorsz.IRepository.TaskIRepository;
import com.saep.taylorsz.Models.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository  //informa para o jpa que essa classe é a que fazer acesso direto com o banco de dados
@Transactional //anotação do EntityManager para acões de inserção com persist, merge e remove
public class TaskRepository implements TaskIRepository {

    private final EntityManager entityManager; //injeção de dependencia via construtor, tem anotação pra isso mas eu não quero

    public TaskRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Task create(Task task) {
        task.setDateRegister(LocalDate.now());
        task.setStatus("A Fazer");
        entityManager.persist(task); //metodo para registrar no banco de dados
        return task;
    }

    @Override
    public Task update(Task task) {
        entityManager.merge(task); //o metodo merge serve para atualizar, mas caso o objeto não existe ele cria
        return task;
    }

    @Override
    public TaskDTO updateStatus(int id, String status) {
        Task task = entityManager.find(Task.class, id);
        task.setStatus(status);
        entityManager.merge(task);
        return new TaskDTO(task.getId(), task.getStatus());
    }

    @Override
    public List<TaskDTO> findAll() { // esse comando grande que so a zorra é um sql nativo, por que no java existe o JPQL
        String sqlCommander = "SELECT \n" +
                "    t.id,\n" +
                "    t.description,\n" +
                "    t.sector_name,\n" +
                "    t.priority,\n" +
                "    t.date_register,\n" +
                "    t.status,\n" +
                "    t.user_id,\n" +
                "    u.nome\n" +
                "FROM tasks t\n" +
                "LEFT JOIN users u ON t.user_id = u.id;\n";
        Query query = entityManager.createNativeQuery(sqlCommander); //Criamos um objeto do tipo Query que vai conter a nossa busca no banco de dados
        List<Object[]> results = query.getResultList(); //guardamos o resultado em um Object[] que por padão o JPA retorna
        List<TaskDTO> tasks = new ArrayList<>(); //criamos uma Lista do tipo UserDTO
        for (Object[] row : results) {
            Long id = ((Number) row[0]).longValue(); // fazemos um cast e cada posição do Object[] se refere a uma linha da tabela
            String description = (String) row[1];
            String sectorName = (String) row[2];
            String priority = (String) row[3];
            LocalDate dateRegister = ((java.sql.Date) row[4]).toLocalDate();
            String status = (String) row[5];
            Long userId = ((Number) row[6]).longValue();
            String userName = (String) row[7];

            TaskDTO taskDTO = new TaskDTO(id, description, sectorName, priority, dateRegister, status, userId, userName);
            tasks.add(taskDTO);
        }
        return tasks;
    }

    @Override
    public Optional<TaskDTO> findBy(Long id) {
        String sqlCommander = "SELECT \n" +
                "    t.id,\n" +
                "    t.description,\n" +
                "    t.sector_name,\n" +
                "    t.priority,\n" +
                "    t.date_register,\n" +
                "    t.status,\n" +
                "    t.user_id,\n" +
                "    u.nome\n" +
                "FROM tasks t\n" +
                "LEFT JOIN users u ON t.user_id = u.id\n" +
                "WHERE t.id = :id";

        Object[] result =  (Object[]) entityManager.createNativeQuery(sqlCommander)
                .setParameter("id", id)
                .getSingleResult();

        TaskDTO taskDTO = new TaskDTO(
                ((Number) result[0]).longValue(),  // id (converte para Long)
                (String) result[1],                // description
                (String) result[2],                // sector_name
                (String) result[3],                // priority
                ((java.sql.Date) result[4]).toLocalDate(), // date_register (converte para LocalDate)
                (String) result[5],                // status
                ((Number) result[6]).longValue(),  // user_id (converte para Long)
                (String) result[7]
        );

        return Optional.ofNullable(taskDTO);
    }

    @Override
    public void delete(int id) { //aqui nem preciso falar ne
        Task task = entityManager.find(Task.class, id);
        entityManager.remove(task);
    }
}
