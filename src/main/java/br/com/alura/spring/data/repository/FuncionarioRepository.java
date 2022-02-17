package br.com.alura.spring.data.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>,
                   JpaSpecificationExecutor<Funcionario> {
//    Derived Query
    List<Funcionario> findByNome(String nome);

//    JPQL - queries criadas através de uma estrutura SQL, porém com os nomes das entidades Java
    @Query("SELECT f FROM Funcionario f WHERE f.nome = :nome "
           + "AND f.salario > :salario AND f.dataContratacao = :data")
    List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);

//    Native Query - queries padrões SQL que conseguimos executar no nosso Client SQL
    @Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data",
            nativeQuery = true)
    List<Funcionario> findDataContratacaoMaior(LocalDate data);

    @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f",
            nativeQuery = true)
    List<FuncionarioProjecao> findFuncionarioSalario();
}
