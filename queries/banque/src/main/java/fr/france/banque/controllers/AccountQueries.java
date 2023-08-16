package fr.france.banque.controllers;

import fr.france.banque.entities.AccountEntity;
import fr.france.banque.entities.GetAllAccounts;
import fr.france.banque.entities.GetOneAccount;
import fr.france.banque.services.AccountService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/queries/account")
public class AccountQueries {

    private AccountService accountService;
    private QueryGateway queryGateway;

    public AccountQueries(AccountService accountService, QueryGateway queryGateway) {
        this.accountService = accountService;
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public CompletableFuture<List<AccountEntity>> getAllAccounts(){
        return queryGateway.query(new GetAllAccounts(), ResponseTypes.multipleInstancesOf(AccountEntity.class));
    }

    @GetMapping("/one/{id}")
    public CompletableFuture<AccountEntity> getOneAccount(@PathVariable String id){
        return queryGateway.query(new GetOneAccount(id), ResponseTypes.instanceOf(AccountEntity.class));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
