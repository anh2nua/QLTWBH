package com.poly.assimentshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.assimentshop.domain.Account;
import com.poly.assimentshop.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	Optional<Customer> findByEmail(String email);
}
