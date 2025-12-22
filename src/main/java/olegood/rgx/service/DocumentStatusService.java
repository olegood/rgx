package olegood.rgx.service;

import olegood.rgx.domain.document.Document;

public interface DocumentStatusService {

    void submit(Document document);

    void approve(Document document);

    void reject(Document document);

    void terminate(Document document);

    void archive(Document document);

    void reopen(Document document);
}
