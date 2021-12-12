package br.com.avaliacao_Sprint3.avaliacaoSprint3.form;

import javax.validation.constraints.NotNull;

import br.com.avaliacao_Sprint3.avaliacaoSprint3.modelo.State;
import br.com.avaliacao_Sprint3.avaliacaoSprint3.repository.StateRepository;

public class AtualizacaoStateForm {
    @NotNull
    private double area;

    @NotNull
    private int populacao;

    public double getArea() {
        return area;
    }

    public int getPopulacao() {
        return populacao;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setPopulacao(int populacao) {
        this.populacao = populacao;
    }

    public State atualizar(Long id, StateRepository stateRepository){
        State state = stateRepository.getById(id);
        state.setArea(this.area);
        state.setPopulacao(populacao);
        return state;
    }
}
