package learn.data;

import learn.entity.CheckingAccount;
import org.springframework.data.repository.CrudRepository;

public interface CheckingAccountRepository extends CrudRepository<CheckingAccount, Integer> {

    CheckingAccount findByAccountNumber(int accountNumber);

    CheckingAccount createCheckingAccount();

    void deposit(String accountType, double amount);

    void withdraw(String accountType, double amount);
}
