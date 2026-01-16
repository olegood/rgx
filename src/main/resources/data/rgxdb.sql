create table organization
(
    id                  bigint       not null
        constraint pk_organization primary key,
    code                varchar(15)  not null
        constraint uk_organization_code unique,
    name                varchar(64)  not null,
    website             varchar(128),
    country             varchar(64)  not null,
    description         varchar(300),
    founded             integer,
    industry            varchar(100) not null,
    number_of_employees integer,
    status              varchar(10)  not null
);

create table enrollment
(
    id              bigint      not null
        constraint pk_enrollment primary key,
    organization_id bigint      not null references organization (id),
    type            varchar(14) not null,
    status          varchar(14) not null
);

create table marker
(
    id            bigint      not null
        constraint pk_marker primary key,
    enrollment_id bigint      not null references enrollment (id),
    type          varchar(10) not null,
    start_date    date        not null,
    end_date      date
);

--

CREATE TABLE research
(
    -- Fields inherited from Project
    id                        UUID         NOT NULL
        CONSTRAINT pk_research PRIMARY KEY,
    organization_id           BIGINT
        CONSTRAINT fk_research_on_organization REFERENCES organization (id),
    code                      VARCHAR(255) NOT NULL
        CONSTRAINT uk_research_code UNIQUE,
    status                    VARCHAR(50)  NOT NULL,
    created_at                TIMESTAMP    NOT NULL,
    modified_at               TIMESTAMP    NOT NULL,

    -- Fields specific to Research
    title                     VARCHAR(255) NOT NULL,
    abstract                  TEXT,

    -- ResearchScope (Embedded)
    domain                    VARCHAR(255) NOT NULL,
    sub_domain                VARCHAR(255),
    constraints               VARCHAR(2000),
    keywords                  VARCHAR(255),

    -- EthicalProfile (Embedded)
    ethics_review_required    BOOLEAN      NOT NULL,
    ethics_status             VARCHAR(50),
    ethics_authority          VARCHAR(255),
    ethical_risk_level        VARCHAR(50),

    -- ComplianceProfile (Embedded)
    regulatory_frameworks     VARCHAR(255),
    internal_compliance_ok    BOOLEAN      NOT NULL,
    last_compliance_review_at TIMESTAMP,

    -- MethodologyProfile (Embedded)
    methodology_type          VARCHAR(100) NOT NULL,
    primary_methods           VARCHAR(2000),
    tools                     VARCHAR(2000),

    -- ConfidenceProfile (Embedded)
    base_confidence           DECIMAL(5, 2),
    method_maturity_factor    DECIMAL(3, 2),
    team_expertise_factor     DECIMAL(3, 2),
    data_availability_factor  DECIMAL(3, 2),

    -- ResearchTimeline (Embedded)
    start_date                DATE         NOT NULL,
    target_end_date           DATE         NOT NULL,
    actual_end_date           DATE
);

CREATE TABLE research_goal
(
    id               UUID          NOT NULL
        CONSTRAINT pk_research_goal PRIMARY KEY,
    research_id      UUID          NOT NULL
        CONSTRAINT fk_research_goal_on_research REFERENCES research (id),
    description      VARCHAR(2000) NOT NULL,
    success_criteria VARCHAR(2000),
    achieved         BOOLEAN       NOT NULL,
    outcome_notes    VARCHAR(2000)
);
