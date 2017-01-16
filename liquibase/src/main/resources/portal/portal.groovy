package portal

databaseChangeLog {
    changeSet(id: 'v.1.0.2-schema', author: 'Serhii_Bohutskyi') {
        sqlFile(path: 'sql/create-db.sql', relativeToChangelogFile: 'true')
    }
    changeSet(id: 'v.1.0.2-static', author: 'Serhii_Bohutskyi') {
        sqlFile(path: 'sql/static-locales.sql', relativeToChangelogFile: 'true')
    }
    changeSet(id: '2016-22-04-0', author: 'Anton Mokshyn <mokshino@gmail.com>') {
        sqlFile(path: 'sql/message-partition.sql', relativeToChangelogFile: 'true', splitStatements: 'false')
    }
}
