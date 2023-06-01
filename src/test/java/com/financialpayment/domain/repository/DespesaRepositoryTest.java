package com.financialpayment.domain.repository;

import com.financialpayment.domain.repsitory.DespesaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DespesaRepositoryTest {

    @Autowired
    private DespesaRepository despesaRepository;

    @Test
    void textExample() throws Exception{
        System.out.println("passando pelo teste...");
        assertThat(despesaRepository.findAll());

    }
}
