package com.JWT_firstTest.securityCrud.entity;



import com.JWT_firstTest.securityCrud.config.StringCryptoConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Convert(converter = StringCryptoConverter.class)
    private String name;
    @Column(name = "dead_line")
    private Date deadLine;
    @Column(name = "is_done")
    private boolean isDone;

}
