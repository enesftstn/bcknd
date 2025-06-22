package com.university.repository;

import com.university.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);
    List<Grade> findBySubjectId(Long subjectId);
    List<Grade> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
    
    @Query("SELECT g FROM Grade g WHERE g.student.id = :studentId ORDER BY g.dateAssigned DESC")
    List<Grade> findByStudentIdOrderByDateDesc(@Param("studentId") Long studentId);
    
    @Query("SELECT AVG(g.gradeValue) FROM Grade g WHERE g.student.id = :studentId AND g.subject.id = :subjectId")
    Double findAverageGradeByStudentAndSubject(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);
}