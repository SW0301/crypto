package com.crypto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pgadmin", schema = "public")
public class Admin {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pgadmin_id_generator")
    @SequenceGenerator(name = "pgadmin_id_generator", sequenceName = "pgadmin_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "secret_key")
    private String secretKey;
}
