package learn.domain;

import learn.data.CheckingAccountRepository;
import learn.data.SavingsAccountRepository;
import learn.domain.models.AccountService;
import learn.domain.models.AppUserService;
import learn.domain.models.TransactionService;
import learn.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    private static int newAccountNumber = 12345678;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Lazy
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private TransactionService transactionService;

    public CheckingAccount createCheckingAccount() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setAccountBalance(new BigDecimal(0.0));
        checkingAccount.setAccountNumber(accountGenerator());

        checkingAccountRepository.save(checkingAccount);

        return checkingAccountRepository.findByAccountNumber(checkingAccount.getAccountNumber());
    }

    public SavingsAccount createSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        savingsAccount.setAccountNumber(accountGenerator());

        savingsAccountRepository.save(savingsAccount);

        return savingsAccountRepository.findByAccountNumber(savingsAccount.getAccountNumber());
    }

    public void deposit(String username, String accountType, double amount) {
        AppUser user = appUserService.findByUsername(username);

        if (accountType.equalsIgnoreCase("Checking")) {
            CheckingAccount checkingAccount = user.getCheckingAccount();
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().add(new BigDecimal(amount)));
            checkingAccountRepository.save(checkingAccount);

            Date date = new Date();

            CheckingTransaction checkingTransaction = new CheckingTransaction(date, "Deposit to Checking Account", "Account", "Finished", amount, checkingAccount.getAccountBalance(), checkingAccount);
            transactionService.saveCheckingDepositTransaction(checkingTransaction);

        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
        }
    }

    public void withdraw(String username, String accountType, double amount) {
        AppUser user = appUserService.findByUsername(username);

        if (accountType.equalsIgnoreCase("Checking")) {
            CheckingAccount checkingAccount = user.getCheckingAccount();
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            checkingAccountRepository.save(checkingAccount);

            Date date = new Date();

            CheckingTransaction checkingTransaction = new CheckingTransaction(date, "Withdraw from Checking Account", "Account", "Finished", amount, checkingAccount.getAccountBalance(), checkingAccount);
            transactionService.saveCheckingWithdrawTransaction(checkingTransaction);

        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);

            Date date = new Date();

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from Savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
        }
    }
    private int accountGenerator() {
        return ++newAccountNumber;
    }
}
