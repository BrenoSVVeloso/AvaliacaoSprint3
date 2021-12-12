package br.com.avaliacao_Sprint3.avaliacaoSprint3.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.avaliacao_Sprint3.avaliacaoSprint3.modelo.Regiao;
import br.com.avaliacao_Sprint3.avaliacaoSprint3.modelo.State;
//Tipo usado no envio de dados para a tela
public class StateDto {
    private Long id;
    private String nome;
    private Regiao regiao;
    private String capital;
    private int populacao;
    private double area;

    public StateDto(State state){
        this.id = state.getId();
        this.nome = state.getNome();
        this.regiao = state.getRegiao();
        this.capital = state.getCapital();
        this.populacao = state.getPopulacao();
        this.area = state.getArea();
    }

    public long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public Regiao getRegiao() {
        return regiao;
    }
    public String getCapital() {
        return capital;
    }

    public int getPopulacao() {
        return populacao;
    }

    public double getArea() {
        return area;
    }


    public static List<StateDto> converter(List<State> states) {
        return states.stream().map(StateDto::new).collect(Collectors.toList());
    }

    public static StateDto converter(State state){
        return new StateDto(state);
    }

}
