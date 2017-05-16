CREATE TABLE IF NOT EXISTS portal_user (
  id bigint auto_increment,
  email varchar(50) NOT NULL,
  first_name varchar(50),
  last_name varchar(50),
  password varchar(200) NOT NULL,
  unique (email)
);

CREATE TABLE IF NOT EXISTS project (
  id bigint auto_increment,
  name varchar(50) NOT NULL,
  owner_id INTEGER NOT NULL references portal_user(id),
  secret_token varchar(50) NOT NULL,
  unique (secret_token)
);

CREATE TABLE IF NOT EXISTS user_permission (
  user_id INTEGER NOT NULL references portal_user(id),
  project_id INTEGER NOT NULL references project(id),
  permission varchar(50) NOT NULL,
  unique (user_id, project_id)
);

CREATE TABLE IF NOT EXISTS message_bundle (
  id bigint auto_increment,
  version varchar(50) NULL,
  uid varchar(50) NOT NULL,
  project_id INTEGER NOT NULL references project(id)
);

CREATE TABLE IF NOT EXISTS language (
  id bigint auto_increment,
  name varchar(50) NULL,
  message_bundle_id INTEGER NOT NULL references message_bundle(id)
);

CREATE TABLE IF NOT EXISTS message (
  key varchar NOT NULL,
  language_id INTEGER NOT NULL references language(id),
  value varchar NULL,
  unique (key, language_id)
);
