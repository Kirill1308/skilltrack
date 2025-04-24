CREATE TABLE departments
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    manager_id  UUID
);

CREATE TABLE teams
(
    id            UUID PRIMARY KEY,
    created_at    TIMESTAMP    NOT NULL,
    name          VARCHAR(255) NOT NULL,
    description   TEXT,
    department_id UUID,
    team_lead_id  UUID,
    CONSTRAINT fk_team_department FOREIGN KEY (department_id) REFERENCES departments (id)
);

CREATE TABLE IF NOT EXISTS skills
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    category    VARCHAR(50)  NOT NULL,
    popularity  INTEGER               DEFAULT 0
);

CREATE TABLE IF NOT EXISTS user_profiles
(
    id                  UUID PRIMARY KEY,
    created_at          TIMESTAMP    NOT NULL,
    updated_at          TIMESTAMP,
    first_name          VARCHAR(255) NOT NULL,
    last_name           VARCHAR(255) NOT NULL,
    email               VARCHAR(255) NOT NULL UNIQUE,
    job_title           VARCHAR(255),
    profile_picture_url VARCHAR(255),
    bio                 TEXT,
    department_id       UUID REFERENCES departments (id),
    team_id             UUID REFERENCES teams (id)
);

ALTER TABLE departments ADD CONSTRAINT fk_department_manager FOREIGN KEY (manager_id) REFERENCES user_profiles (id);
ALTER TABLE teams ADD CONSTRAINT fk_team_lead FOREIGN KEY (team_lead_id) REFERENCES user_profiles (id);

CREATE TABLE IF NOT EXISTS user_skills
(
    id                  UUID PRIMARY KEY,
    user_id             UUID      NOT NULL REFERENCES user_profiles (id),
    skill_id            UUID      NOT NULL REFERENCES skills (id),
    proficiency_level   VARCHAR(20),
    years_of_experience INTEGER,
    is_verified         BOOLEAN            DEFAULT FALSE,
    verified_by         VARCHAR(255),
    verification_date   DATE,
    notes               TEXT,
    last_used_date      DATE,
    CONSTRAINT user_skill_unique UNIQUE (user_id, skill_id)
);

CREATE TABLE IF NOT EXISTS user_assessment_submission_references
(
    user_id                  UUID NOT NULL REFERENCES user_profiles (id),
    assessment_submission_id UUID NOT NULL,
    PRIMARY KEY (user_id, assessment_submission_id)
);

CREATE TABLE IF NOT EXISTS certifications
(
    id                   UUID PRIMARY KEY,
    user_id              UUID         NOT NULL REFERENCES user_profiles (id),
    name                 VARCHAR(255) NOT NULL,
    description          TEXT,
    issuing_organization VARCHAR(255) NOT NULL,
    issued_at            TIMESTAMP,
    expires_at           TIMESTAMP,
    validity_period      INTEGER,
    credential_id        VARCHAR(255) UNIQUE,
    credential_url       VARCHAR(255)
);

CREATE INDEX idx_user_skills_user_id ON user_skills (user_id);
CREATE INDEX idx_user_skills_skill_id ON user_skills (skill_id);
CREATE INDEX idx_certifications_user_id ON certifications (user_id);
CREATE INDEX idx_user_profiles_department_id ON user_profiles (department_id);
CREATE INDEX idx_user_profiles_team_id ON user_profiles (team_id);
