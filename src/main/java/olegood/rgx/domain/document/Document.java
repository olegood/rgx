package olegood.rgx.domain.document;

import lombok.Getter;
import olegood.rgx.domain.project.Project;

@Getter
public class Document {

    private Long id;

    private Project project;

    private String title;
    private String content;

    private DocumentStatus status = DocumentStatus.DRAFT;

    private String owner;

}
