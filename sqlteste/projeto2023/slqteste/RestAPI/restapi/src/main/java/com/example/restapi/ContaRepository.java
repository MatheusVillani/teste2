package com.example.restapi;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long>{
    List<Conta> findBySaldo(Double saldo);
    List<Conta> findByAgencia(String agencia);
}
