package com.indocyber.winterhold.repository;

import com.indocyber.winterhold.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
