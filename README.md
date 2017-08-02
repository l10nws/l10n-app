# L10n.ws

### L10n.ws is a system for simplifying the localization and internationalization process of your product 
For more information check [https://l10n.ws](https://l10n.ws)

Localization system contains in sub-projects:
- [Portal](https://github.com/l10nws/l10n-app/tree/master/l10n-portal)
- [API application](https://github.com/l10nws/l10n-app/tree/master/l10n-api)


# [Portal](https://l10n.ws/download/)
Application with UI for managing localization content 

## Getting Started

You can build latest version of portal from source code with gradle or [download](https://l10n.ws/download/) artifact.

### Prerequisites

JRE and Servlet Container must be installed for using L10n applications.
[PostgreSQL](https://www.postgresql.org/) database need to be installed as well. 
We recommend to use [Tomcat 7](https://tomcat.apache.org/download-70.cgi)

## Deployment

Copy artifact in 'webapps' directory in installed servlet container and start it.


## Built With

* [Gradle](https://gradle.org/) - Dependency Management

## Database Versioning 

We use [Liquibase](http://www.liquibase.org/) for database versioning.  

## Authors

* **Serhii Bohutskyi** - [github](https://github.com/serhiibh)
* **Anton Mokshin**  - [github](https://github.com/mokshino)

## License

This project is licensed under the Apache License - see the [LICENSE.md](LICENSE.md) file for details
