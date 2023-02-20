package learn.data;

import learn.entity.CheckingAccount;
import learn.entity.CheckingTransaction;
import learn.entity.SavingsAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CheckingTransactionRepository extends CrudRepository<CheckingTransaction, Integer> {

}
