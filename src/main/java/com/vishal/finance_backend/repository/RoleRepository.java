package com.vishal.finance_backend.repository;

import com.vishal.finance_backend.Entity.Role;
import com.vishal.finance_backend.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName name);
}
