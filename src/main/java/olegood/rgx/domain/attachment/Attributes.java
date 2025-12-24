package olegood.rgx.domain.attachment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Attributes {

  @Column(name = "FILE_NAME")
  private String fileName;

  @Column(name = "CONTENT_TYPE")
  private String contentType;

  @Column(name = "LOCATION")
  private String location;

  @Column(name = "CHECKSUM")
  private String checksum;

  @Column(name = "SIZE")
  private long size;
}
