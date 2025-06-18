package com.saep.taylorsz.Models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //informa para o jpa que essa classe é um mapeamento para o banco de dados
@Table(name = "tasks") //informa o nome da tabela mas por padrão ele ja pega pelo nome da classe
@Data //manda o lombok fazer os getters/setters, toString e hasCode
@AllArgsConstructor //manda o lombok criar um construtor com todos os atributos da classe
@NoArgsConstructor //manda o lombok criar um contrutor sem atributos ou contrutor vazio
@Builder //se eu não me engano ele manda o lombok criar um construtor com atributos opcionais, mas eu não tenho certeza wenst
public class Task {

    @Id //define que é uma chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //faz o auto incremento e decide o tipo de estrategia que sera usada
    private Long id;

    //relacionamento entre as tabelas, a forma de ler é: muitas tarefas tem um usuario
    @ManyToOne(fetch = FetchType.LAZY) //informa para o jpa que sera uma busca preguiçosa, então ele so vai buscar os dados caso você chame: task.getUser()
    @JoinColumn(name = "user_id", nullable = false) //diferente do @Column, o @JoinColumn serve para mapear a chave estrangeira do relacionamento
    private User user;

    @Column(nullable = false) //define atributos basicos da coluna como, not null, unique, lenght etc
    private String description;

    @Column(nullable = false)
    private String sectorName;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private LocalDate dateRegister;

    @Column(nullable = false)
    private String status;
}
