package com.financialpayment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancialPaymentApplication  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FinancialPaymentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
