package fr.france.banque.commands;

import lombok.Getter;

public class CreditAccountComand extends BaseCommand<String>{
    @Getter
    private String currency;
    @Getter
    private double amount;

    public CreditAccountComand(String id, String currency, double amount) {
        super(id);
        this.currency = currency;
        this.amount = amount;
    }
}
