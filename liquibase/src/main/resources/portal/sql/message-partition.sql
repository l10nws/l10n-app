CREATE SEQUENCE message_id_seq;

CREATE TABLE message (
  id BIGINT DEFAULT nextval('message_id_seq') PRIMARY KEY,
  version_id      INTEGER     NOT NULL,
  locale_id       VARCHAR     NOT NULL REFERENCES locales (id),
  key             TEXT        NOT NULL,
  value           TEXT
);

CREATE OR REPLACE FUNCTION bundle_version_insert_trigger_function() RETURNS TRIGGER AS
$$
  BEGIN
    EXECUTE 'CREATE TABLE message_' || NEW.id || ' (id BIGINT DEFAULT nextval(''message_id_seq'') PRIMARY KEY, CHECK (version_id = ' || NEW.id || '), UNIQUE (locale_id, key)) INHERITS (message)';
    RETURN NEW;
  END;
$$

LANGUAGE plpgsql;

CREATE TRIGGER bundle_version_insert_trigger AFTER INSERT ON bundle_version FOR EACH ROW EXECUTE PROCEDURE bundle_version_insert_trigger_function();

CREATE OR REPLACE FUNCTION bundle_version_delete_trigger_function() RETURNS TRIGGER AS
  $$
  BEGIN
    EXECUTE 'DROP TABLE message_' || OLD.id;
    RETURN OLD;
  END;
$$

LANGUAGE plpgsql;

CREATE TRIGGER bundle_version_delete_trigger AFTER DELETE ON bundle_version FOR EACH ROW EXECUTE PROCEDURE bundle_version_delete_trigger_function();

CREATE OR REPLACE FUNCTION message_insert_trigger_function() RETURNS TRIGGER AS
$$
  BEGIN
    EXECUTE 'insert into message_' || NEW.version_id || '(version_id, locale_id, key, value) values($1, $2, $3, $4)'
      USING NEW.version_id, NEW.locale_id, NEW.key, NEW.value;
    RETURN NULL;
  END;
$$

LANGUAGE plpgsql;

CREATE TRIGGER message_insert_trigger BEFORE INSERT ON message FOR EACH ROW EXECUTE PROCEDURE message_insert_trigger_function();
