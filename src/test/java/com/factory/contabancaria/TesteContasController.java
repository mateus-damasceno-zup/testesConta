package com.factory.contabancaria;

import com.factory.contabancaria.DTO.ContasDTORespostaPost;
import com.factory.contabancaria.controller.ContasController;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.service.ContasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContasController.class)
public class TesteContasController {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContasService contasService;

    @Test
    void testaCriaContaDeposito() throws Exception {
        ContaFactory contaFactory = new ContaFactory();
        String tipoServico = contaFactory.tipoServicoConta("deposito").toString();
        ContasModel contasModel = new ContasModel(1L, "1", "1", "Damian",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(100), tipoServico, BigDecimal.valueOf(1100));
        when(contasService.cadastrar(any(ContasModel.class), any(ContaFactory.class))).thenReturn(contasModel);

        mockMvc.perform(post("/api/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomeDoUsuario\":\"Damian\", \"valorAtualConta\":1000, \"valorFornecido\":100, \"tipoServico\":\"deposito\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeDoUsuario").value("Damian"))
                .andExpect(jsonPath("$.valorAtualConta").value(1000))
                .andExpect(jsonPath("$.valorFornecido").value(100))
                .andExpect(jsonPath("$.tipoServico").value(String.valueOf(tipoServico)))
                .andDo(print());

        verify(contasService, times(1)).cadastrar(any(ContasModel.class), any(ContaFactory.class));
    }

    @Test
    void testaCriaContaSaque() throws Exception {
        ContaFactory contaFactory = new ContaFactory();
        String tipoServico = contaFactory.tipoServicoConta("saque").toString();
        ContasModel contasModel = new ContasModel(1L, "1", "1", "Damian",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(100), tipoServico, BigDecimal.valueOf(900));
        when(contasService.cadastrar(any(ContasModel.class), any(ContaFactory.class))).thenReturn(contasModel);

        mockMvc.perform(post("/api/contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomeDoUsuario\":\"Damian\", \"valorAtualConta\":1000, \"valorFornecido\":100, \"tipoServico\":\"saque\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeDoUsuario").value("Damian"))
                .andExpect(jsonPath("$.valorAtualConta").value(1000))
                .andExpect(jsonPath("$.valorFornecido").value(100))
                .andExpect(jsonPath("$.tipoServico").value(String.valueOf(tipoServico)));

        verify(contasService, times(1)).cadastrar(any(ContasModel.class), any(ContaFactory.class));

    }

    @Test
    void testaListaContas() throws Exception {
        ContaFactory contaFactory = new ContaFactory();
        String tipoServicoS = contaFactory.tipoServicoConta("saque").toString();
        ContasModel contasModel = new ContasModel(1L, "1", "1", "Damian",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(100), tipoServicoS, BigDecimal.valueOf(900));

        String tipoServicoD = contaFactory.tipoServicoConta("deposito").toString();
        ContasModel contasModel1 = new ContasModel(2L, "1", "1", "Damian",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(100), tipoServicoD, BigDecimal.valueOf(1100));

        List<ContasModel> contasModelList = new ArrayList<>();
        contasModelList.add(contasModel);
        contasModelList.add(contasModel1);

        when(contasService.listarContas()).thenReturn(contasModelList);

        List<ContasDTORespostaPost> dtoList = contasModelList.stream()
                .map(conta -> {
                    ContasDTORespostaPost dto = new ContasDTORespostaPost();

                    dto.setNomeDoUsuario(conta.getNomeDoUsuario());
                    dto.setValorAtualConta(conta.getValorAtualConta());
                    dto.setValorFornecido(conta.getValorFornecido());
                    dto.setTipoServico(conta.getTipoServico());
                    return dto;
                })
                .collect(Collectors.toList());

        String expectedJson = new ObjectMapper().writeValueAsString(dtoList);

        mockMvc.perform(get("/api/contas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andDo(print());

        verify(contasService).listarContas();
    }
    @Test
    void testaExibiContaPorID() throws Exception {
        ContaFactory contaFactory = new ContaFactory();
        String tipoServico = contaFactory.tipoServicoConta("saque").toString();
        ContasModel contasModel = new ContasModel(1L, "1", "1", "Damian",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(100), tipoServico, BigDecimal.valueOf(900));

        when(contasService.exibeContaPorId(1L)).thenReturn(Optional.of(contasModel));

        mockMvc.perform(get("/api/contas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numConta").value(contasModel.getNumConta()))
                .andExpect(jsonPath("$.agencia").value(contasModel.getAgencia()))
                .andExpect(jsonPath("$.nomeDoUsuario").value(contasModel.getNomeDoUsuario()))
                .andExpect(jsonPath("$.valorAtualConta").value(contasModel.getValorAtualConta().doubleValue()))
                .andDo(print());
    verify(contasService).exibeContaPorId(1L);
    }
    @Test
    void testaAtualizarConta() throws Exception {
        ContaFactory contaFactory = new ContaFactory();
        String tipoServico = contaFactory.tipoServicoConta("saque").toString();

        ContasModel contasModel = new ContasModel(1L, "1", "1", "Damian",
                BigDecimal.valueOf(1000), BigDecimal.valueOf(100), tipoServico, BigDecimal.valueOf(900));

        when(contasService.exibeContaPorId(1L)).thenReturn(Optional.of(contasModel));
        when(contasService.alterar(eq(1L), any(ContasModel.class), any(ContaFactory.class)))
                .thenReturn(contasModel);

        mockMvc.perform(put("/api/contas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numConta\":\"NovoNumero\", \"agencia\":\"novaAGencia\",\"tipoServico\":\"saque\",\"valorFornecido\": 100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numConta").value("1"))
                .andExpect(jsonPath("$.agencia").value("1"))
                .andExpect(jsonPath("$.tipoServico").value(tipoServico))
                .andExpect(jsonPath("$.valorFornecido").value(100.0))
                .andDo(print());

        verify(contasService).alterar(eq(1L), any(ContasModel.class), any(ContaFactory.class));
    }


    @Test
    public void testeDeletaConta() throws Exception{
        mockMvc.perform(delete("/api/contas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify that alunoService.deletaAluno was called with the correct ID
        verify(contasService).deletarConta(1L);


    }
}



