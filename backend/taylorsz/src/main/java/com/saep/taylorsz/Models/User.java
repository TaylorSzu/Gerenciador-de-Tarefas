package com.saep.taylorsz.Models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //informa para o jpa que essa classe é um mapeamento para o banco de dados
@Table(name = "users") //informa o nome da tabela mas por padrão ele ja pega pelo nome da classe
@Data //manda o lombok fazer os getters/setters, toString e hasCode
@AllArgsConstructor //manda o lombok criar um construtor com todos os atributos da classe
@NoArgsConstructor //manda o lombok criar um contrutor sem atributos ou contrutor vazio
@Builder //se eu não me engano ele manda o lombok criar um construtor com atributos opcionais, mas eu não tenho certeza wenst
public class User {

    @Id //define que é uma chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //faz o auto incremento e decide o tipo de estrategia que sera usada
    private Long id;

    @Column(nullable = false) //define atributos basicos da coluna como, not null, unique, lenght etc
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    //relacionamento entre as tabelas, a forma de ler é: um usuario tem muitas tarefas
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

}
