package com.factory.contabancaria.service;

import com.factory.contabancaria.mapping.MappingService;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContasService {
    @Autowired
    private ContasRepository contasRepository;
    @Autowired
    private MappingService mappingService;

    public ContasService(ContasRepository contasRepository, MappingService mappingService) {
        this.contasRepository = contasRepository;
        this.mappingService = mappingService;
    }

    //métodos
    public List<ContasModel> listarContas() {
        return contasRepository.findAll();
    }

    public Optional<ContasModel> exibeContaPorId(Long id) {
        return contasRepository.findById(id);
    }

    public ContasModel cadastrar(ContasModel contasModel, ContaFactory contaFactory) {
        BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipoServico())
                .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());
        contasModel.setValorFinal(resultado);
        return contasRepository.save(contasModel);
    }

    public ContasModel alterar(Long id, ContasModel contasModel, ContaFactory contaFactory) {

        ContasModel conta = exibeContaPorId(id).get();

        if (contasModel.getNumConta() != null) {
            conta.setNumConta(contasModel.getNumConta());
        }
        if (contasModel.getAgencia() != null) {
            conta.setAgencia(contasModel.getAgencia());
        }
        if (contasModel.getTipoServico() != null) {
            BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipoServico())
                    .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());
            conta.setValorFornecido(contasModel.getValorFornecido());
            conta.setValorFinal(resultado);
            conta.setTipoServico(contasModel.getTipoServico());
        }

        return contasRepository.save(conta);
    }

    public ContasModel alterarUmDado(Long id, ContasModel contasModel, String campo, ContaFactory contaFactory) {

        ContasModel conta = exibeContaPorId(id).get();

        if (contasModel.getNumConta() != null && campo != null &&
                campo.equalsIgnoreCase("numConta")) {
            conta.setNumConta(contasModel.getNumConta());
        }
        if (contasModel.getAgencia() != null && campo != null &&
                campo.equalsIgnoreCase("agencia")) {

            conta.setAgencia(contasModel.getAgencia());
        }
        if (contasModel.getNomeDoUsuario() != null && campo != null &&
                campo.equalsIgnoreCase("nomeDoUsuario")) {

            conta.setNomeDoUsuario(contasModel.getNomeDoUsuario());
        }
        if (contasModel.getValorAtualConta() != null && campo != null &&
                campo.equalsIgnoreCase("valorAtualConta")) {

            conta.setValorAtualConta(contasModel.getValorAtualConta());
        }

        if (contasModel.getNomeDoUsuario() != null && campo != null &&
                campo.equalsIgnoreCase("valorFinal")) {

            conta.setValorFinal(contasModel.getValorFinal());
        }
        if (contasModel.getTipoServico() != null && campo != null &&
                campo.equalsIgnoreCase("tipoServico")) {
            BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipoServico())
                    .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());
            conta.setValorFornecido(contasModel.getValorFornecido());
            conta.setValorFinal(resultado);
            conta.setTipoServico(contasModel.getTipoServico());
        }

        return contasRepository.save(conta);
    }

    public void deletarConta(Long id) {
        contasRepository.deleteById(id);
    }


    public List<ContasModel> exibeContaPorNome(String nome) {
        return contasRepository.findByName(nome);
    }
}
