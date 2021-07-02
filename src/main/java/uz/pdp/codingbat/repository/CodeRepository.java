package uz.pdp.codingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbat.entity.Code;

public interface CodeRepository extends JpaRepository<Code,Integer> {
}
