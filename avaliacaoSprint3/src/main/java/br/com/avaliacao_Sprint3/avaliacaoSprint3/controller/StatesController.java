package br.com.avaliacao_Sprint3.avaliacaoSprint3.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.avaliacao_Sprint3.avaliacaoSprint3.dto.StateDto;
import br.com.avaliacao_Sprint3.avaliacaoSprint3.form.AtualizacaoStateForm;
import br.com.avaliacao_Sprint3.avaliacaoSprint3.form.StateForm;
import br.com.avaliacao_Sprint3.avaliacaoSprint3.modelo.Regiao;
import br.com.avaliacao_Sprint3.avaliacaoSprint3.modelo.State;
import br.com.avaliacao_Sprint3.avaliacaoSprint3.repository.StateRepository;
//Controller do estado
@RequestMapping("/api/states")
@RestController
public class StatesController {

    @Autowired
    private StateRepository statesRepository;


    //Lista todos os estados.
    @GetMapping("")
    public List<StateDto> lista(){
        List<State> states = statesRepository.findAll();
        return StateDto.converter(states);
    }

    //Cadastra um estado
    @PostMapping
    @Transactional
    public ResponseEntity<StateDto> cadastrar(@RequestBody @Valid StateForm form, UriComponentsBuilder uriBuilder){
        State state = form.converter();
        statesRepository.save(state);
        URI uri = uriBuilder.path("/api/states/{id}").buildAndExpand(state.getId()).toUri();
        return ResponseEntity.created(uri).body(new StateDto(state));
    }


    //Lista um Estado com base em seu id, com a variável passada no path
    @GetMapping("/{id}")
    public ResponseEntity<StateDto> listaUmEstado(@PathVariable Long id){
        Optional<State> optional = statesRepository.findById(id);
        //Verifica se existe esse id
        if(optional.isPresent()){
            State state = optional.get();
            return ResponseEntity.ok(StateDto.converter(state));
        }
        return ResponseEntity.notFound().build();
    }

    //Lista os estados por região a partir de um query param, método GET
    //@GetMapping
    @RequestMapping(value = "", params = "regiao", method = RequestMethod.GET)
    public List<StateDto> listaPorRegiao(@RequestParam(required = false, name = "regiao") String regiao){
        
        try {
            //Verifica se a região é nula ou válida
            if(regiao == null ||  !(Arrays.stream(Regiao.values()).anyMatch(t -> t.name().equals(regiao)))){

                throw new NullPointerException();

            }else{
                //Transforma a string região no enum Região
                Regiao regiaoFiltrada = Regiao.valueOf(regiao);
                List<State> states = statesRepository.findByRegiao(regiaoFiltrada);
                return  StateDto.converter(states);
            }    
        } catch (NullPointerException npe) {
            System.err.println("Região inválida ou nula");
        }
        return Collections.emptyList();
    }

    //Lista os estados de acordo com a população. A query param população pode ter valor ASC OU DESC
    //@GetMapping
    @RequestMapping(value = "", params = "populacao", method = RequestMethod.GET)
    public List<StateDto> listaEstadosPorMaiorPopulacao(@RequestParam(required = false, name = "populacao") String regiao){
        List<State> states = statesRepository.findAll();
        //Ordena os estados de acordo com a população
        states.sort(Comparator.comparing(State::getPopulacao));
        //Se a query for ASC retorna
        if(regiao.equals("ASC")){
            
            return StateDto.converter(states);
        
        //Se for DESC reverte e retorna
        }else if(regiao.equals("DESC")){
            Collections.reverse(states);
            return StateDto.converter(states);
        }
        
        return Collections.emptyList();  
    }

    //Lista os estados de acordo com a área. A query param área pode ter valor ASC OU DESC
    //@GetMapping
    @RequestMapping(value = "", params = "area", method = RequestMethod.GET)
    public List<StateDto> listaEstadosPorMaiorArea(@RequestParam(required = false, name = "area") String area){
        List<State> states = statesRepository.findAll();
        //Ordena os estados de acordo com a área
        states.sort(Comparator.comparing(State::getArea));
        //Se a query for ASC retorna
        if(area.equals("ASC")){
            return StateDto.converter(states);
        //Se for DESC reverte e retorna
       }else if(area.equals("DESC")){
            
            Collections.reverse(states);
            return StateDto.converter(states);
       }

       return Collections.emptyList();
    }

    //Deleta um estado de acordo com o ID, com a variável passada no path
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        Optional<State> optional = statesRepository.findById(id);
        //Verifica se o Estado existe
        if(optional.isPresent()){
            statesRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Atualiza um estado de acordo com o ID, com a variável passada no path
    @PutMapping("/{id}")
    public ResponseEntity<StateDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoStateForm form){
        Optional<State> optional = statesRepository.findById(id);
        //Verifica se o Estado existe
        if(optional.isPresent()){
            State state = form.atualizar(id, statesRepository);
            return ResponseEntity.ok(new StateDto(state));
        }

        return ResponseEntity.notFound().build();
    }
}
