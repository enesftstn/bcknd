package com.university.repository;

import com.university.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUsername(String username);
    List<Teacher> findByDepartmentContainingIgnoreCase(String department);
    
    @Query("SELECT t FROM Teacher t WHERE t.firstName LIKE %:name% OR t.lastName LIKE %:name%")
    List<Teacher> findByNameContaining(@Param("name") String name);
}