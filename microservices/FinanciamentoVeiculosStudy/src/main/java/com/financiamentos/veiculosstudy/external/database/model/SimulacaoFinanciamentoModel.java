package com.financiamentos.veiculosstudy.external.database.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@Table(name = "simulacao_financiamento")
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class SimulacaoFinanciamentoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private ClienteModel cliente;
    private float valorEntrada;
    private float valorFinanciamento;
    private int parcelas;
    private float valorParcela;
    private LocalDateTime dataSimulacao;
}
