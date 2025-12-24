package olegood.rgx.domain.attachment;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import olegood.rgx.domain.document.Document;

@Data
@Entity
@Table(name = "ATTACHMENT")
public class Attachment {

  @Id private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DOCUMENT_ID")
  private Document document;

  @Embedded private Attributes attributes;

  @Column(name = "UPLOADED_AT")
  private String uploadedAt;
}
