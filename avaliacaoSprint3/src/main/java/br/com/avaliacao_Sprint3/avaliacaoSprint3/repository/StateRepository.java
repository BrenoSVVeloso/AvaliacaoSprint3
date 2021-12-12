package br.com.avaliacao_Sprint3.avaliacaoSprint3.repository;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.avaliacao_Sprint3.avaliacaoSprint3.modelo.Regiao;
import br.com.avaliacao_Sprint3.avaliacaoSprint3.modelo.State;
//Usado para acessar o banco de dados
public interface StateRepository extends JpaRepository<State,Long>{
    
    List<State> findByRegiao(Regiao regiaoState);

    State findByNome(@NotNull @NotEmpty @Length(min = 4) String nome);

}
