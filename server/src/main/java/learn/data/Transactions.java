package learn.data;

import learn.entity.CheckingAccount;
import learn.entity.SavingsAccount;

public interface Transactions {

    void transferringBalanceBetweenAccounts(String transferFrom, String transferTo, String amount, CheckingAccount checkingAccount, SavingsAccount savingsAccount) throws Exception;
}
