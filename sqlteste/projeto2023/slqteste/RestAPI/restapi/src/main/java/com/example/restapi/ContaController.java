package com.example.restapi;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.*;

@Tag(name = "Conta", description = "Conta management APIs")

@RestController
public class ContaController {
    @Autowired
    private ContaRepository repositorio;
    
    @Operation(
      summary = "Retorna todas as contas presentes no Banco de Dados",
      description = "Retorna todas as contas."
 )
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Conta.class), mediaType = "application/json") }),
      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
  
    @GetMapping("/apicontas")
    public List<Conta> listar(){
        return repositorio.findAll();
    }
    @Operation(
        summary = "Retorna a conta do ID especificado",
        description = "Retorna a conta especificada."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Conta.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    
    @GetMapping("/contas/{id}")
    public ResponseEntity<Conta> getTcontaById(@PathVariable(value = "id") Long id) {
        Optional<Conta> tconta = repositorio.findById(id);
    
        if (tconta.isPresent()) {
            return new ResponseEntity<Conta>(tconta.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException();
        }
    }
    @Operation(
        summary = "Cria uma conta no Banco de Dados",
        description = "Método usado para criaçao de contas.")
    
        @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Conta.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/contas")
    public Conta postConta(@RequestBody Conta conta) {
        return repositorio.save(conta);
    }
    @Operation(
        summary = "Atualiza uma conta do ID especificado",
        description = "Método usado para atualização.")
  
   
    @PutMapping("/contas/{id}")
    public ResponseEntity<Conta> atualizarConta(@PathVariable(value = "id") Long id, @RequestBody Conta contaDetails) throws ResourceNotFoundException {
        Conta conta = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        
        Conta novaConta = new Conta();
        novaConta.setId(contaDetails.getId());
        novaConta.setNome(contaDetails.getNome());
        novaConta.setSaldo(contaDetails.getSaldo());
        novaConta.setAgencia(contaDetails.getAgencia());
        
        
        final Conta contaAtualizada = repositorio.save(novaConta);
        repositorio.delete(conta);

        return ResponseEntity.ok(contaAtualizada);
    }
    @Operation(
        summary = "Deleta a conta do ID especificado",
        description = "Método usado para deletar.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Conta.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    
    @DeleteMapping ("/contas/{id}")
    public ResponseEntity<?> deleteConta(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
    Conta conta = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    
    repositorio.delete(conta);
    
    return ResponseEntity.ok().build();
    }
    @Operation(
        summary = "Método de filtro que retorna as contas que possuem o saldo especificado na busca",
        description = "Método de filtragem.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Conta.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    
    @GetMapping("/saldocontas")
    public List<Conta> listar(@RequestParam(value = "saldo", required = false) Double saldo){
        if (saldo != null) {
            return repositorio.findBySaldo(saldo);
        } else {
            return repositorio.findAll();
        }
    }
    @Operation(
        summary = "Método de filtro que retorna as contas que possuem a agencia especificada na busca ",
        description = "Método de retorno.")
        @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Conta.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/contas")
    public List<Conta> listar(@RequestParam(value = "agencia", required = false) String agencia){
        if (agencia != null) {
            return repositorio.findByAgencia(agencia);
        } else {
            return repositorio.findAll();
        }
    }
}