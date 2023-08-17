package com.factory.contabancaria.controller;

import com.factory.contabancaria.DTO.ContaDTORespostaGet;
import com.factory.contabancaria.DTO.ContasDTORespostaPost;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import com.factory.contabancaria.service.ContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/contas")
public class ContasController {

    @Autowired
    ContasService contasService;

    @Autowired
    ContasRepository contasRepository;

    //requisições
    //GET - Pegar as informações do nosso banco
    @GetMapping
    public ResponseEntity<List<ContasModel>> listarTodasContas() {
        return ResponseEntity.ok(contasService.listarContas());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> exibeUmaContaPeloId(@PathVariable Long id) {
        Optional<ContasModel> contaOpcional = contasService.exibeContaPorId(id);
        if (contaOpcional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }
        ContaDTORespostaGet contaDTORespostaGet = new ContaDTORespostaGet();
        contaDTORespostaGet.setNumConta(contaOpcional.get().getNumConta());
        contaDTORespostaGet.setAgencia(contaOpcional.get().getNumConta());
        contaDTORespostaGet.setNomeDoUsuario(contaOpcional.get().getNomeDoUsuario());
        contaDTORespostaGet.setValorAtualConta(contaOpcional.get().getValorAtualConta());
        return ResponseEntity.ok(contaDTORespostaGet);
    }

    //- Crie um endpoint GET que selecione uma conta pelo nome do usuário.
    @GetMapping(path="/buscaPorNome/{nome}")
    public ResponseEntity<?> exibeUmaContaPeloNome(@PathVariable String nome){

      List<ContasModel> contasModelList = contasService.exibeContaPorNome(nome);
        if(contasModelList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario nao encontrado pelo nome");
        }
        List<ContaDTORespostaGet> contaDTORespostaGetList = new ArrayList<>();

        for(ContasModel contasModel : contasModelList){
            ContaDTORespostaGet contaDTORespostaGet = new ContaDTORespostaGet();
            contaDTORespostaGet.setNumConta(contasModel.getNumConta());

            contaDTORespostaGet.setAgencia(contasModel.getAgencia());
            contaDTORespostaGet.setNomeDoUsuario(contasModel.getNomeDoUsuario());
            contaDTORespostaGet.setValorAtualConta(contasModel.getValorAtualConta());
            contaDTORespostaGetList.add(contaDTORespostaGet);
        }


        return ResponseEntity.ok(contaDTORespostaGetList);
    }

    //POST - Cria uma nova conta dentro do banco
//    @PostMapping
//    public ResponseEntity<ContasModel> cadastrarConta(@RequestBody ContasModel contasModel, ContaFactory contaFactory) {
//        ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);
//        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
//    }

    //- Crie DTOs para que a resposta do usuário seja apenas os seguintes atributos:
    // requisição POST:
    //	- nomeDoUsuario
    //- valorAtualConta
    //- valorFornecido
    //- tipoServico
    @PostMapping
    public ResponseEntity<?> cadastraContaDTO(@RequestBody ContasModel contasModel, ContaFactory contaFactory) {
        ContasModel contaNova = contasService.cadastrar(contasModel,contaFactory);
        ContasDTORespostaPost contasDTORespostaPost = new ContasDTORespostaPost();

        contasDTORespostaPost.setNomeDoUsuario(contaNova.getNomeDoUsuario());
        contasDTORespostaPost.setValorAtualConta(contaNova.getValorAtualConta());
        contasDTORespostaPost.setValorFornecido(contaNova.getValorFornecido());
        contasDTORespostaPost.setTipoServico(contaNova.getTipoServico());
        return new ResponseEntity<>(contasDTORespostaPost, HttpStatus.CREATED);
    }

    //PUT - Alterar uma conta já existente dentro do banco
    @PutMapping(path = "/{id}")
    public ContasModel atualizarConta(@PathVariable Long id, @RequestBody ContasModel contasModel, ContaFactory contaFactory) {
        return contasService.alterar(id, contasModel, contaFactory);
    }
    @PutMapping(path = "/{id}/{campo}")
    public ContasModel alterarUmDado(@PathVariable Long id, @PathVariable String campo, @RequestBody ContasModel contasModel, ContaFactory contaFactory) {
        return contasService.alterarUmDado(id,contasModel, campo, contaFactory);
    }

    //DELETE - Deleta uma conta já existente dentro do banco
    @DeleteMapping(path = "/{id}")
    public void deletarConta(@PathVariable Long id) {
        contasService.deletarConta(id);
    }

}
