package com.example.banqueservice.repositories;

import com.example.banqueservice.entities.Compte;
import com.example.banqueservice.entities.Transaction;
import com.example.banqueservice.entities.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author
 **/
public interface TransactionRepositorie extends JpaRepository<Transaction, Long> {

    List<Transaction> findTransactionByCompte(Compte compte);
    @Query("select sum(t.montant) from Transaction t where t.type = :type")
    Double sumByType(TypeTransaction type);

}
