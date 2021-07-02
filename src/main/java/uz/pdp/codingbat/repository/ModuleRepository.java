package uz.pdp.codingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbat.entity.Module;

public interface ModuleRepository extends JpaRepository<Module,Integer> {
}
