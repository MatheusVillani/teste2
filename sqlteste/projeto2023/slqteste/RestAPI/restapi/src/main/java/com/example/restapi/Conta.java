package com.example.restapi;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="contas")
public class Conta {

    @Id
    private Long id;
    private String nome;
    private long saldo;
    private Long agencia;
   

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public long getSaldo() {
        return saldo;
    }
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }
    public Long getAgencia() {
        return agencia;
    }
    public void setAgencia(Long agencia) {
        this.agencia = agencia;
    }
}