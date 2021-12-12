package br.com.avaliacao_Sprint3.avaliacaoSprint3.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.avaliacao_Sprint3.avaliacaoSprint3.modelo.Regiao;
import br.com.avaliacao_Sprint3.avaliacaoSprint3.modelo.State;
//Tipo usado na coleta dos dados do usuário
public class StateForm {

    @NotNull @Length(min = 4)
    private String nome;

    @NotNull
    private Regiao regiao;

    @NotNull
    private String capital;

    @NotNull
    private double area;

    @NotNull
    private int populacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getPopulacao() {
        return populacao;
    }

    public void setPopulacao(int populacao) {
        this.populacao = populacao;
    }

    public State converter() {
        return new State(this.nome, this.regiao.name(), this.populacao, this.capital, this.area);
    }
}
