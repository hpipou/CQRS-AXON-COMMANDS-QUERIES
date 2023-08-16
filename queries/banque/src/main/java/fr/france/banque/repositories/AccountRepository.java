package fr.france.banque.repositories;

import fr.france.banque.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
}
