package learn.data;

import learn.entity.SavingsAccount;
import org.springframework.data.repository.CrudRepository;

public interface SavingsAccountRepository extends CrudRepository<SavingsAccount, Integer> {

    SavingsAccount findByAccountNumber(int accountNumber);

    SavingsAccount createSavingsAccount();

    void deposit(String accountType, double amount);

    void withdraw(String accountType, double amount);
}
