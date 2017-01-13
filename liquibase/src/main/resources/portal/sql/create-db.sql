DROP TABLE IF EXISTS portal_user CASCADE;
DROP TABLE IF EXISTS project CASCADE;
DROP TABLE IF EXISTS user_permission CASCADE;
DROP TABLE IF EXISTS message_bundle CASCADE;
DROP TABLE IF EXISTS locale_version CASCADE;
DROP TABLE IF EXISTS persistent_logins CASCADE;
DROP TABLE IF EXISTS locales CASCADE;
DROP TABLE IF EXISTS bundle_version CASCADE;
DROP TABLE IF EXISTS auth_token CASCADE;

CREATE TABLE IF NOT EXISTS portal_user (
  id           SERIAL       PRIMARY KEY,
  email        VARCHAR(64)  NOT NULL,
  first_name   VARCHAR(64),
  last_name    VARCHAR(64),
  role         VARCHAR(100) NOT NULL DEFAULT 'ROLE_USER',
  password     VARCHAR(200) NOT NULL,
  access_token VARCHAR(64)  NOT NULL,
  UNIQUE (email),
  UNIQUE (access_token)
);

CREATE TABLE IF NOT EXISTS auth_token (
  user_id      INTEGER      NOT NULL REFERENCES portal_user (id),
  auth_token   VARCHAR(64)  NOT NULL,
  update_date  TIMESTAMP    NOT NULL,
  UNIQUE (auth_token)
);

CREATE TABLE IF NOT EXISTS project (
  id            SERIAL PRIMARY KEY,
  name          VARCHAR(64) NOT NULL,
  owner_id      INTEGER     NOT NULL REFERENCES portal_user (id),
  description   TEXT,
  creation_date TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS user_permission (
  user_id    INTEGER     NOT NULL REFERENCES portal_user (id),
  project_id INTEGER     NOT NULL REFERENCES project (id),
  permission VARCHAR(64) NOT NULL,
  UNIQUE (user_id, project_id)
);

CREATE TABLE IF NOT EXISTS locales (
  id       VARCHAR NOT NULL PRIMARY KEY,
  language VARCHAR NOT NULL,
  country  VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS message_bundle (
  id            SERIAL PRIMARY KEY,
  label         VARCHAR(64) NULL,
  uid           VARCHAR(64) NOT NULL,
  owner_id      INTEGER     NOT NULL REFERENCES portal_user (id),
  creation_date TIMESTAMP   NOT NULL,
  project_id    INTEGER     NOT NULL REFERENCES project (id)
);

CREATE TABLE IF NOT EXISTS bundle_version (
  id                SERIAL PRIMARY KEY,
  version           VARCHAR   NOT NULL,
  creation_date     TIMESTAMP NOT NULL,
  modification_date TIMESTAMP,
  view_date TIMESTAMP,
  default_locale_id VARCHAR   NOT NULL REFERENCES locales (id),
  message_bundle_id INTEGER   NOT NULL REFERENCES message_bundle (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS locale_version (
  id         SERIAL PRIMARY KEY,
  locale_id  VARCHAR NOT NULL REFERENCES locales (id),
  version_id INTEGER NOT NULL REFERENCES bundle_version (id) ON DELETE CASCADE
);

CREATE TABLE persistent_logins (
  username  VARCHAR(64) NOT NULL,
  series    VARCHAR(64) PRIMARY KEY,
  token     VARCHAR(64) NOT NULL,
  last_used TIMESTAMP   NOT NULL
);


