package learn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class CheckingAccount {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private BigDecimal accountBalance;
    private int accountNumber;
    private int routingNumber;

    @OneToMany(mappedBy="checking_account", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonIgnore
    private List<CheckingTransaction> checkingTransactionsList;

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

    public List<CheckingTransaction> getCheckingTransactionsList() {
        return checkingTransactionsList;
    }

    public void setCheckingAccountList(List<CheckingTransaction> checkingTransactionsList) {
        this.checkingTransactionsList = checkingTransactionsList;
    }
}
