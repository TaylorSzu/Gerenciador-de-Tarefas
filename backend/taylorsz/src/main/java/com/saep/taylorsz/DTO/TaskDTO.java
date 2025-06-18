package com.saep.taylorsz.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String description;
    private String sectorName;
    private String priority;
    private LocalDate dateRegister;
    private String status;
    private Long userId;
    private String userName;

    // getters e setters

    public TaskDTO(Long id, String status) {
        this.id = id;
        this.status = status;
    }
}

