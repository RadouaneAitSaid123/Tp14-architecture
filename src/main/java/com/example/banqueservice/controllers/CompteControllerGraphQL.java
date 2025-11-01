package com.example.banqueservice.controllers;

import com.example.banqueservice.entities.Compte;
import com.example.banqueservice.repositories.CompteRepositorie;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * @author radouane
 **/

@Controller
@AllArgsConstructor
public class CompteControllerGraphQL {

    private CompteRepositorie compteRepositorie;

    @QueryMapping
    public List<Compte> allComptes() {
        return compteRepositorie.findAll();
    }

    @QueryMapping
    public Compte compteById(@Argument Long id) {
        Compte compte = compteRepositorie.findById(id).orElse(null);
        if (compte == null) throw new RuntimeException(String.format("Compte %s not found", id));
        else return compte;
    }

    @MutationMapping
    public Compte saveCompte(@Argument Compte compte) {
        return compteRepositorie.save(compte);
    }

    @QueryMapping
    public Map<String, Object> totalSolde() {
        long count = compteRepositorie.count();
        double sum = compteRepositorie.sumSoldes();
        double average = count > 0 ? sum / count : 0;

        return Map.of(
                "count", count,
                "sum", sum,
                "average", average
        );
    }
}
