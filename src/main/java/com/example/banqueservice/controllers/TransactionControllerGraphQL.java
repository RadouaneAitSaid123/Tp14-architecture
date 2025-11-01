package com.example.banqueservice.controllers;

import com.example.banqueservice.entities.Compte;
import com.example.banqueservice.entities.Transaction;
import com.example.banqueservice.entities.TypeTransaction;
import com.example.banqueservice.repositories.CompteRepositorie;
import com.example.banqueservice.repositories.TransactionRepositorie;
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
public class TransactionControllerGraphQL {

    private CompteRepositorie compteRepositorie;
    private TransactionRepositorie transactionRepositorie;

    @MutationMapping
    public Transaction saveTransaction(@Argument Transaction transaction) {

        compteRepositorie.findById(transaction.getCompte().getId())
                .orElseThrow(() -> new RuntimeException("Compte not found with id "));
        return transactionRepositorie.save(transaction);
    }

    @QueryMapping
    public List<Transaction> compteTransactions(@Argument Long id) {
        Compte compte = compteRepositorie.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte not found"));
        return transactionRepositorie.findTransactionByCompte(compte);
    }

    @QueryMapping
    public Map<String, Object> transactionStats() {
        long count = transactionRepositorie.count();
        Double sumDepotsRaw = transactionRepositorie.sumByType(TypeTransaction.DEPOT);
        Double sumRetraitsRaw = transactionRepositorie.sumByType(TypeTransaction.RETRAIT);

        double sumDepots = sumDepotsRaw != null ? sumDepotsRaw : 0.0;
        double sumRetraits = sumRetraitsRaw != null ? sumRetraitsRaw : 0.0;

        return Map.of(
                "count", count,
                "sumDepots", sumDepots,
                "sumRetraits", sumRetraits
        );
    }
}
