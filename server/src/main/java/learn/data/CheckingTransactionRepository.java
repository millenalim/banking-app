package learn.data;

import learn.entity.CheckingTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CheckingTransactionRepository extends CrudRepository<CheckingTransaction, Integer> {

    List<CheckingTransaction> findAll();
}
