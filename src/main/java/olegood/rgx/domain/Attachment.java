package olegood.rgx.domain;

import olegood.rgx.domain.document.Document;

public class Attachment {

  private Long id;

  private Document document;
  private String fileName;
  private String checksum;
  private String uploadedAt;
}
