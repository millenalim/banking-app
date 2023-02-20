package learn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="savings_account")
public class SavingsAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal accountBalance;
    private int accountNumber;
    private int routingNumber;

    @OneToMany(mappedBy="savingsAccount", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    @JsonIgnore
    private List<SavingsTransaction> savingsTransactionsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(int routingNumber) {
        this.routingNumber = routingNumber;
    }

    public List<SavingsTransaction> getSavingsTransactionsList() {
        return savingsTransactionsList;
    }

    public void setSavingsTransactionsList(List<SavingsTransaction> savingsTransactionsList) {
        this.savingsTransactionsList = savingsTransactionsList;
    }
}
