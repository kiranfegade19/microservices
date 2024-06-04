package com.org.users.users.repositories;

import com.org.users.users.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Customer, Long> {

    public Optional<Customer> findByMobileNumber(String mobileNumber);

    public void deleteByCustomerId(Long customerId);
}
