package fr.france.banque.events;

import lombok.Getter;

public class CreditedAccountEvent extends BaseEvent<String>{

    @Getter private String currency;
    @Getter private double amount;
    public CreditedAccountEvent(String id, String currency, double amount) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}
