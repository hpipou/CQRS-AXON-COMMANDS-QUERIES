package fr.france.banque.aggregates;

import fr.france.banque.commands.CreateAccountCommand;
import fr.france.banque.commands.CreditAccountComand;
import fr.france.banque.commands.DebitAccountCommand;
import fr.france.banque.events.CreatedAccountEvent;
import fr.france.banque.events.CreditedAccountEvent;
import fr.france.banque.events.DebitedAccountEvent;
import fr.france.banque.exceptions.CustomError;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String id;
    private String currency;
    private double balance;

    public AccountAggregate(){
        // this is for AXON
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command){
        if(command.getInitialBalance()<0){
            throw new CustomError("NEGATIVE INITIAL BALANCE");
        }
        AggregateLifecycle.apply(new CreatedAccountEvent(
                UUID.randomUUID().toString(),
                command.getCurrency(),
                command.getInitialBalance()
        ));
    }

    @EventSourcingHandler
    public void on(CreatedAccountEvent event){
        this.id=event.getId();
        this.currency=event.getCurrency();
        this.balance=event.getBalance();
    }

    @CommandHandler
    public void handle(CreditAccountComand command){
        if(command.getAmount()<0){
            throw new CustomError("NEGATIVE INITIAL BALANCE");
        }
        AggregateLifecycle.apply(new CreditedAccountEvent(
                command.getId(),
                command.getCurrency(),
                command.getAmount()
        ));
    }

    @EventSourcingHandler
    public void on(CreditedAccountEvent event){
        this.balance+=event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command){

        if(command.getAmount()<0){
            throw new CustomError("NEGATIVE BALANCE DEBIT");
        }

        if(command.getAmount()>this.balance){
            throw new CustomError("INSUFFICIENT DEBIT");
        }

        AggregateLifecycle.apply(new DebitedAccountEvent(
                command.getId(),
                command.getCurrency(),
                command.getAmount()
        ));

    }

    @EventSourcingHandler
    public void handle(DebitedAccountEvent event){
        this.balance-=event.getAmount();
    }

}
