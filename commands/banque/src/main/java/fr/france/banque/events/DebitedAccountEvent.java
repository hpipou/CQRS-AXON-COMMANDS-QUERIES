package fr.france.banque.events;

import lombok.Getter;

public class DebitedAccountEvent extends BaseEvent<String>{

    @Getter
    private String currency;

    @Getter
    private double amount;

    public DebitedAccountEvent(String id, String currency, double amount) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}
