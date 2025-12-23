package olegood.rgx.domain.document;

import jakarta.persistence.*;
import java.util.function.Predicate;
import lombok.Data;
import olegood.rgx.domain.project.Project;
import olegood.rgx.predicate.IsActionAllowed;
import olegood.rgx.predicate.document.status.CanBeSubmitted;

@Data
@Entity
@Table(name = "DOCUMENT")
public class Document {

  @Id
  @Column(name = "ID")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PROJECT_ID")
  private Project project;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "CONTENT")
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  private DocumentStatus status = DocumentStatus.DRAFT;

  @Column(name = "OWNER")
  private String owner;

  public boolean isActionAllowed(DocumentAction action) {
    return ensure(new IsActionAllowed(action));
  }

  public boolean canBeSubmitted() {
    return ensure(new CanBeSubmitted());
  }

  private boolean ensure(Predicate<Document> predicate) {
    return predicate.test(this);
  }

  public boolean canBeApprovedAutomatically() {
    return false;
  }
}
