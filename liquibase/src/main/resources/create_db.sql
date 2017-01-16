/*#######################################################################################*/

SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'l10n-portal';

/*#######################################################################################*/

DROP DATABASE "l10n-portal";

/*#######################################################################################*/
DO
$body$
BEGIN
  IF NOT EXISTS(
      SELECT *
      FROM pg_catalog.pg_user
      WHERE usename = 'l10n-portal')
  THEN

    CREATE ROLE "l10n-portal" LOGIN PASSWORD '1';
  END IF;
END
$body$;

/*#######################################################################################*/

CREATE DATABASE "l10n-portal" OWNER "l10n-portal";
