package fr.france.banque.controllers;

import fr.france.banque.commands.CreateAccountCommand;
import fr.france.banque.commands.CreditAccountComand;
import fr.france.banque.commands.DebitAccountCommand;
import fr.france.banque.dtos.CreateAccountRequestDTO;
import fr.france.banque.dtos.CreditAccountDTO;
import fr.france.banque.dtos.DebitAccountDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/account")
public class AccountController {

    private CommandGateway commandGateway;

    public AccountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public CompletableFuture<String> CreateNewAccount(@RequestBody CreateAccountRequestDTO requestDTO){
        return commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                requestDTO.getCurrency(),
                requestDTO.getInitialBalance()
        ));
    }

    @PostMapping("/credit")
    public CompletableFuture<String> CreditAccount(@RequestBody CreditAccountDTO requestDTO){
        return commandGateway.send(new CreditAccountComand(
                requestDTO.getId(),
                requestDTO.getCurrency(),
                requestDTO.getAmount()
        ));
    }

    @PostMapping("/debit")
    public CompletableFuture<String> DebitAccount(@RequestBody DebitAccountDTO requestDTO){
        return commandGateway.send(new DebitAccountCommand(
                requestDTO.getId(),
                requestDTO.getCurrency(),
                requestDTO.getAmount()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
