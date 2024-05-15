package team_pwp.swap_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team_pwp.swap_be.entity.Test;

@Repository
public interface TestJpaRepository extends JpaRepository<Test, Long> {

}
