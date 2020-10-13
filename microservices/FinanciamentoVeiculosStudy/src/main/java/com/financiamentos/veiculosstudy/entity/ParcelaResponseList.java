package com.financiamentos.veiculosstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class ParcelaResponseList {
    private List<ParcelaResponse> parcelas;
}
