package com.financiamentos.veiculosstudy.external.database.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "parcelas")
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ParcelaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantidade;
    private float taxaJuros;
}
