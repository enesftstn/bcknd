@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    public Grade findById(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + id));
    }

    public Grade save(Grade grade) {
        return gradeRepository.save(grade);
    }

    public Grade update(Long id, Grade gradeDetails) {
        Grade grade = findById(id);
        grade.setValue(gradeDetails.getValue());
        grade.setDescription(gradeDetails.getDescription());
        return gradeRepository.save(grade);
    }

    public void delete(Long id) {
        Grade grade = findById(id);
        gradeRepository.delete(grade);
    }

    public List<Grade> findByStudentId(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public List<Grade> findBySubjectId(Long subjectId) {
        return gradeRepository.findBySubjectId(subjectId);
    }

    public List<Grade> findByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        return gradeRepository.findByStudentIdAndSubjectId(studentId, subjectId);
    }
}