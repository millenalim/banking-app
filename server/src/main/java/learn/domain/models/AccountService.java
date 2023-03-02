package learn.domain.models;

import learn.entity.CheckingAccount;
import learn.entity.SavingsAccount;

import java.security.Principal;

public interface AccountService {

    CheckingAccount createCheckingAccount();
    SavingsAccount createSavingsAccount();
    void deposit(String username, String accountType, double amount, Principal principal);
    void withdraw(String username, String accountType, double amount, Principal principal);
}
