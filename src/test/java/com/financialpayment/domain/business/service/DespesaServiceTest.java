package com.financialpayment.domain.business.service;

import com.financialpayment.business.filter.DespesaFilter;
import com.financialpayment.business.service.DespesaService;
import com.financialpayment.domain.repsitory.DespesaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DespesaServiceTest {


    @Autowired
    DespesaService despesaService;

    @Test
    public void getDespesaByTipoTest(){

    }

    @Test
    public void testTotalDespesas(){

    }

}
