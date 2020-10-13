package com.financiamentos.veiculosstudy.entity.endpoint;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.financiamentos.veiculosstudy.serializer.IsoLocalDateDeserializer;
import com.financiamentos.veiculosstudy.serializer.IsoLocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ClienteRequest {
    private String nome;
    private String cpf;

    @JsonSerialize(using = IsoLocalDateSerializer.class)
    @JsonDeserialize(using = IsoLocalDateDeserializer.class)
    private LocalDate dataNascimento;
    private String telefone;
    private float renda;
    private boolean possuiImovel;
}
