package uz.pdp.codingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbat.entity.Course;

public interface CourseRepository extends JpaRepository<Course,Integer> {
}
