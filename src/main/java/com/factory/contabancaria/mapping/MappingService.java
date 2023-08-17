package com.factory.contabancaria.mapping;

import com.factory.contabancaria.DTO.ContasDTORespostaPost;
import com.factory.contabancaria.model.ContasModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingService {
    @Autowired
    private ModelMapper modelMapper;

    public ContasDTORespostaPost mapContaToDTO(ContasModel contasModel){
        return modelMapper.map(contasModel,ContasDTORespostaPost.class);
    }
    public ContasModel mapDTOToConta(ContasDTORespostaPost contasDTORespostaPost){
        return modelMapper.map(contasDTORespostaPost,ContasModel.class);
    }


}
