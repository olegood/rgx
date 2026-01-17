package olegood.rgx.domain.project.type.research;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearchRepository extends JpaRepository<Research, UUID> {}
