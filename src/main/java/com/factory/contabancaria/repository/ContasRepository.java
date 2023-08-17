package com.factory.contabancaria.repository;

import com.factory.contabancaria.model.ContasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContasRepository extends JpaRepository<ContasModel, Long> {
    @Query("SELECT c FROM ContasModel c WHERE c.nomeDoUsuario = :nome")
    List<ContasModel> findByName(String nome);
}

