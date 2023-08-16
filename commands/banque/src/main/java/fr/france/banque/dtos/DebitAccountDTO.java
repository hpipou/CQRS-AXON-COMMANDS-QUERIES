package fr.france.banque.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebitAccountDTO {

    private String id;
    private String currency;
    private double amount;

}
