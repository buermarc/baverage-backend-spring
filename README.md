# Beverage Monitoring Backend

Backend written in JAVA with Spring Boot, using Gradle as a build tool.
To start the Backend run

## External Dependencies:

- MQTT Broker
- MySQL Database

We need a running MQTT Broker and a MySQL or MariaDB database. At the moment
those are provided through container started on a Raspberry PI using the Balena
Cloud. To configure the backend we use environment Variables as seen in
`./src/main/resources/application.yml`

### Environment variables

- ${MQTT_SA} - MQTT Server Address
- ${MQTT_SP} - MQTT Server Port
- ${MYSQL_SA} - MYSQL Server Address
- ${MYSQL_SP} - MYSQL Server Port
- ${MYSQL_U} - MYSQL  Username
- ${MYSQL_P} - MYSQL Password

## How to start

To start the backend. Make sure that above listed needed services are running
and corresponding environment variables are set.

```shell
$ gradle bootRun
```
