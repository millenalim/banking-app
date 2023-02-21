package learn.domain;

import learn.entity.CheckingAccount;
import learn.entity.SavingsAccount;

public interface AccountService {

    CheckingAccount createCheckingAccount();
    SavingsAccount createSavingsAccount();
    void deposit(String username, String accountType, double amount);
    void withdraw(String username, String accountType, double amount);
}
