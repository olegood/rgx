package olegood.rgx.domain.project;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.Instant;

public class ProjectListener {

  @PrePersist
  void onPersist(Project project) {
    var now = Instant.now();
    project.setCreatedAt(now);
    project.setModifiedAt(now);
  }

  @PreUpdate
  void onUpdate(Project project) {
    project.setModifiedAt(Instant.now());
  }
}
