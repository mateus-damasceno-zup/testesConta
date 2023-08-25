package com.factory.contabancaria.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_CONTAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContasModel {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 6, nullable = false)
    private String numConta;
    @Column(length = 4, nullable = false)
    private String agencia;
    @Column(nullable = false)
    private String nomeDoUsuario;
    @Column(nullable = false)
    private BigDecimal valorAtualConta;
    @Column(nullable = false)
    private BigDecimal ValorFornecido;
    @Column(length = 20, nullable = false)
    private String tipoServico;
    private BigDecimal valorFinal;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumConta() {
        return numConta;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNomeDoUsuario() {
        return nomeDoUsuario;
    }

    public void setNomeDoUsuario(String nomeDoUsuario) {
        this.nomeDoUsuario = nomeDoUsuario;
    }

    public BigDecimal getValorAtualConta() {
        return valorAtualConta;
    }

    public void setValorAtualConta(BigDecimal valorAtualConta) {
        this.valorAtualConta = valorAtualConta;
    }

    public BigDecimal getValorFornecido() {
        return ValorFornecido;
    }

    public void setValorFornecido(BigDecimal valorFornecido) {
        ValorFornecido = valorFornecido;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }
}
