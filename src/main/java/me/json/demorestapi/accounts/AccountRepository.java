package me.json.demorestapi.accounts;

import com.sun.tools.doclint.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String username);
}
