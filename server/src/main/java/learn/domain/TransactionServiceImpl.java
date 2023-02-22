package learn.domain;

import learn.data.*;
import learn.domain.models.AppUserService;
import learn.domain.models.TransactionService;
import learn.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CheckingTransactionRepository checkingTransactionRepository;

    @Autowired
    private SavingsTransactionRepository savingsTransactionRepository;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private RecipientRepository recipientRepository;

    public List<CheckingTransaction> findCheckingTransactionList(String username) {
        AppUser user = appUserService.findByUsername(username);
        List<CheckingTransaction> checkingTransactionList = user.getCheckingAccount().getCheckingTransactionsList();

        return checkingTransactionList;
    }
    public List<SavingsTransaction> findSavingsTransactionList(String username) {
        AppUser user = appUserService.findByUsername(username);
        List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount().getSavingsTransactionsList();

        return savingsTransactionList;
    }

    public void saveCheckingDepositTransaction(CheckingTransaction checkingTransaction) {
        checkingTransactionRepository.save(checkingTransaction);
    }

    public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionRepository.save(savingsTransaction);
    }

    public void saveCheckingWithdrawTransaction(CheckingTransaction checkingTransaction) {
        checkingTransactionRepository.save(checkingTransaction);
    }

    public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionRepository.save(savingsTransaction);
    }

    public void transferBetweenAccounts(String transferFrom, String transferTo, String amount, CheckingAccount checkingAccount, SavingsAccount savingsAccount) throws Exception {
        if (transferFrom.equalsIgnoreCase("Checking") && transferTo.equalsIgnoreCase("Savings")) {
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            checkingAccountRepository.save(checkingAccount);
            savingsAccountRepository.save(savingsAccount);

            Date date = new Date();

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Between account transfer from" + transferFrom + " to " + transferTo, "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(),savingsAccount);
            savingsTransactionRepository.save(savingsTransaction);
        } else {
            throw new Exception("Invalid Transfer");
        }
    }

    public List<Recipient> findRecipientList(Principal principal) {
        String user = principal.getName();
        List<Recipient> recipientList = recipientRepository.findAll().stream()
                .filter(recipient -> user.equals(recipient.getAppUser().getUsername()))
                .collect(Collectors.toList());

        return recipientList;
    }

    public Recipient saveRecipient(Recipient recipient) {
        return recipientRepository.save(recipient);
    }

    public Recipient findRecipientByName(String recipientName) {
        return recipientRepository.findByRecipientName(recipientName);
    }

    public void deleteRecipientByName(String recipientName) {
        recipientRepository.deleteByName(recipientName);
    }

    public void transferToSomeone(Recipient recipient, String accountType, String amount, CheckingAccount checkingAccount, SavingsAccount savingsAccount) {
        if (accountType.equalsIgnoreCase("Checking")) {
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            checkingAccountRepository.save(checkingAccount);

            Date date = new Date();

            CheckingTransaction checkingTransaction = new CheckingTransaction(date, "Transfer to recipient " + recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), checkingAccount.getAccountBalance(), checkingAccount);
            checkingAccountRepository.save(checkingAccount);
        } else if (accountType.equalsIgnoreCase("Savings")) {
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountRepository.save(savingsAccount);

            Date date = new Date();

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer to recipient " + recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
            savingsTransactionRepository.save(savingsTransaction);
        }
    }


}
