package uz.pdp.codingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbat.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}
