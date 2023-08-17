package com.factory.contabancaria.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTORespostaGet {
    private String numConta;
    private String agencia;

    private String nomeDoUsuario;

    private BigDecimal valorAtualConta;

}
