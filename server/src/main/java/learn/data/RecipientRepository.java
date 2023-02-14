package learn.data;

import learn.entity.Recipient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipientRepository extends CrudRepository<Recipient, Integer> {

    List<Recipient> findAll();

    Recipient findByName(String recipientName);

    void deleteByName(String recipientName);
}
