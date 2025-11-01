package com.example.banqueservice;

import com.example.banqueservice.entities.Compte;
import com.example.banqueservice.entities.TypeCompte;
import com.example.banqueservice.repositories.CompteRepositorie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class BanqueServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BanqueServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner init(CompteRepositorie compteRepositorie) {
        return args -> {
            Compte compte = new Compte(null, 300, new Date(), TypeCompte.EPARGNE);
            Compte compte2 = new Compte(null, 400, new Date(), TypeCompte.COURANT);

            compteRepositorie.save(compte);
            compteRepositorie.save(compte2);

            double solde = compteRepositorie.sumSoldes();
            System.out.println(solde);
        };
    }
}
