package learn.data;

import learn.entity.CheckingAccount;
import learn.entity.Recipient;
import learn.entity.SavingsAccount;
import org.springframework.data.repository.CrudRepository;

import java.security.Principal;
import java.util.List;

public interface RecipientRepository extends CrudRepository<Recipient, Integer> {

    List<Recipient> findAll();

    Recipient findByRecipientName(String recipientName);

    void deleteByName(String recipientName);

    List<Recipient> findRecipient(Principal principal);

    void deleteRecipientByName(String recipientName);

    void transferToSomeone(Recipient recipient, String accountType, String amount, CheckingAccount checkingAccount, SavingsAccount savingsAccount);
}
