package com.financiamentos.veiculosstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class ParcelaResponse {
    private int meses;
    private float valor;
}
