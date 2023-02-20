package learn.domain;

import learn.entity.*;

import java.security.Principal;
import java.util.List;

public interface TransactionService {
    List<CheckingTransaction> findCheckingTransactionList(String username);

    List<SavingsTransaction> findSavingsTransactionList(String username);

    void saveCheckingDepositTransaction(CheckingTransaction checkingTransaction);

    void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);

    void saveCheckingWithdrawTransaction(CheckingTransaction checkingTransaction);

    void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);

    void transferBetweenAccounts(String transferFrom, String transferTo, String amount, CheckingAccount checkingAccount, SavingsAccount savingsAccount) throws Exception;

    List<Recipient> findRecipientList(Principal principal);

    Recipient saveRecipient(Recipient recipient);

    Recipient findRecipientByName(String recipientName);

    void deleteRecipientByName(String recipientName);

    void transferToSomeone(Recipient recipient, String accountType, String amount, CheckingAccount checkingAccount, SavingsAccount savingsAccount);
}
