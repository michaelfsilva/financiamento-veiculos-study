package com.financiamentos.veiculosstudy.external.database.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "score")
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ScoreModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String faixa;
    private int pontuacao;
    private float juros;
}
