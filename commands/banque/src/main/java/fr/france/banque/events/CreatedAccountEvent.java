package fr.france.banque.events;

import lombok.Getter;

public class CreatedAccountEvent extends BaseEvent<String>{
    @Getter
    private String currency;
    @Getter
    private double balance;

    public CreatedAccountEvent(String id, String currency, double balance) {
        super(id);
        this.currency = currency;
        this.balance = balance;
    }
}
