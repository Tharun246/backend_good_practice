package com.example.APIGuide.Repository;

import com.example.APIGuide.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long>
{
    boolean existsByEmail(String email);

    @Query("""
            select e from Employee e
            where e.email = :email
            and
            e.id != :id
            """)
    Optional<Employee> duplicateCheck(@Param("email") String email,@Param("id") Long id);

    @Query("""
            select e.email 
            from Employee e 
            where e.id = :id
            """)
    Optional<String> getEmail(@Param("id") Long id);

    Page<Employee> findByDepartment(Pageable pageable, String department);
}
