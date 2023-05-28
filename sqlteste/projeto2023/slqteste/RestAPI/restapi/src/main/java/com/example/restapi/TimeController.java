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

@Tag(name = "Time", description = "Time management APIs")
@RestController
public class TimeController {
    @Autowired
    private TimeRepository repositorio;
    @Operation(
        summary = "Retorna todos os times presentes no Banco de Dados",
        description = "Retorna todos os times."
   )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Time.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    
    @GetMapping("/apitimes")
    public List<Time> listar(){
        return repositorio.findAll();
    }
    @Operation(
        summary = "Retorna o time do ID especificado",
        description = "Retorna o time especificado."
    
   )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Time.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    
    @GetMapping("/times/{id}")
    public ResponseEntity<Time> getTimeById(@PathVariable(value = "id") Long id) {
        Optional<Time> time = repositorio.findById(id);
    
        if (time.isPresent()) {
            return new ResponseEntity<Time>(time.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException();
        }
    }
    @Operation(
        summary = "Cria um time no Banco de Dados",
        description = "Método usado para criaçao de times.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Time.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    
    @PostMapping("/times")
    public Time postTime(@RequestBody Time time) {
        return repositorio.save(time);
    }
    @Operation(
        summary = "Deleta o time do ID especificado",
        description = "Nétodo usado para deletar times."
   )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Time.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    
    @DeleteMapping ("/times/{id}")
    public ResponseEntity<?> deleteTime(@PathVariable(value = "id") Long id) {
        Time time = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException());
        repositorio.delete(time);
        return ResponseEntity.ok().build();
    }
    @Operation(
        summary = "Atualiza um time do ID especificado",
        description = "Método usado para atualização.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Time.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    
    @PutMapping("/times/{id}")
    public ResponseEntity<Time> updateTime(@PathVariable(value = "id") Long id, @RequestBody Time timeDetails) {
        Time time = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    
        time.setId(timeDetails.getId());
        time.setNome(timeDetails.getNome());
        time.setAno(timeDetails.getAno());
        time.setCidade(timeDetails.getCidade());
        time.setEstado(timeDetails.getEstado());
    
        final Time updatedTime = repositorio.save(time);
        return ResponseEntity.ok(updatedTime);
    }
    @Operation(
        summary = "Método de filtro que retorna os times que foram fundados no ano especificado na busca",
        description = "Método de filtragem.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Time.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    
    @GetMapping("/times")
    public List<Time> listar(@RequestParam(value = "ano", required = false) String ano){
        if (ano != null) {
            return repositorio.findByAno(ano);
        } else {
            return repositorio.findAll();
        }
    }
}
