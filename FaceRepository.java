package mokserver.mokapi.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaceRepository extends JpaRepository<Face, Long> {
    List<Face> findByUuid(String uuid);
    void deleteByUuid(String uuid);
}
