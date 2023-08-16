package fr.france.banque.services;

import fr.france.banque.entities.AccountEntity;
import fr.france.banque.entities.GetAllAccounts;
import fr.france.banque.entities.GetOneAccount;
import fr.france.banque.events.CreatedAccountEvent;
import fr.france.banque.events.CreditedAccountEvent;
import fr.france.banque.events.DebitedAccountEvent;
import fr.france.banque.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Slf4j
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventHandler
    public void on(CreatedAccountEvent event, EventMessage eventMessage){
        log.info("=================================== ");
        log.info(event.getId());
        log.info("=================================== ");
        AccountEntity account = new AccountEntity();
        account.setId(event.getId());
        account.setCurrency(event.getCurrency());
        account.setBalance(event.getBalance());
        account.setCreatedAt(eventMessage.getTimestamp());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(CreditedAccountEvent event){
        log.info("=================================== ");
        log.info("CREDIT => " + event.getAmount());
        log.info("=================================== ");
        AccountEntity account= accountRepository.findById(event.getId()).orElse(null);
        if(account!=null){
            double newBalance = account.getBalance() + event.getAmount();
            account.setBalance(newBalance);
            accountRepository.save(account);
        }
    }

    @EventHandler
    public void on(DebitedAccountEvent event){
        log.info("=================================== ");
        log.info("DEBIT => " + event.getAmount());
        log.info("=================================== ");
        AccountEntity account= accountRepository.findById(event.getId()).orElse(null);
        if(account!=null){
            double newBalance = account.getBalance() - event.getAmount();
            account.setBalance(newBalance);
            accountRepository.save(account);
        }

    }

    @QueryHandler
    public List<AccountEntity> GetAllAccount(GetAllAccounts query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public AccountEntity GetOneAccount(GetOneAccount query){
        return accountRepository.findById(query.getId()).orElse(null);
    }


}
