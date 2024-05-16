package team_pwp.swap_be.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;
import team_pwp.swap_be.entity.image.Image;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {

}
