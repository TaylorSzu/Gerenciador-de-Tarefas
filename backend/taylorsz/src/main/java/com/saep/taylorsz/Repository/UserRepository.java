package com.saep.taylorsz.Repository;

import com.saep.taylorsz.DTO.UserDTO;
import com.saep.taylorsz.IRepository.UserIRepository;
import com.saep.taylorsz.Models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@Repository //informa para o jpa que essa classe é a que fazer acesso direto com o banco de dados
@Transactional //anotação do EntityManager para acões de inserção com persist, merge e remove
public class UserRepository implements UserIRepository {

    private final EntityManager entityManager; //injeção de dependencia via construtor, tem anotação pra isso mas eu não quero

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User create(User user) {
        entityManager.persist(user); //metodo para registrar no banco de dados
        return user;
    }

    @Override
    public List<UserDTO> findAll() { //rapaz, aqui é meio doido mas vamos la
        String sqlCommander = "SELECT id, nome From users"; //aqui é um comando sql nativo, aqui existe tambem o JPQL que é quase igual
        Query query = entityManager.createNativeQuery(sqlCommander); //Criamos um objeto do tipo Query que vai conter a nossa busca no banco de dados
        List<Object[]> results = query.getResultList(); //guardamos o resultado em um Object[] que por padão o JPA retorna
        List<UserDTO> users = new ArrayList<>(); //criamos uma Lista do tipo UserDTO
        for (Object[] row : results) {
            Long id = ((Number) row[0]).longValue(); // fazemos um cast e cada posição do Object[] se refere a uma linha da tabela
            String nome = (String) row[1];

            UserDTO userDTO = new UserDTO(id, nome);
            users.add(userDTO);
        }
        return users; //não preciso nem dizer o que faz ne :/
    }
}
