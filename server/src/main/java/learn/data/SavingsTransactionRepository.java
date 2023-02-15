package learn.data;

import learn.entity.SavingsTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SavingsTransactionRepository extends CrudRepository<SavingsTransaction, Integer> {

    List<SavingsTransaction> findAll();

    List<SavingsTransaction> findSavingsTransactionList(String username);
}
