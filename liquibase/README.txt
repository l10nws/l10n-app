
====================================== PREPARE DATABASE ======================================

1) create roles 'l10n-portal' with password '1'

2) create databases 'l10n-portal'

3) set corresponding 'l10n-portal' roles for each 'public' schemes

    pgAdmin -> database -> public -> Properties -> set owner - >ok

4)  go to ..root/l10n-liquibase and run liquibase scripts
    - for windows:
    ..\gradlew update -PrunList=portal

====================================== USING LIQUIBASE ======================================

1)  For existing database create run

        gradlew changelogSync

2)  To execute changelogs run

        gradlew update

======================================
