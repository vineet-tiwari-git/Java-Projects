# RDBMS-Data-Fetcher

Simple Java program to pull large volumes of data from relational database. In my experience whenever I had to pull large volumes of data from database the DB client would die on me while writing the records. This has happened multiple times therefore I decided to develop a simple script for. Came in handy numerous times. ;)

## Installation

```bash
mvn clean package
java -jar RDBMS-Data-Fetcher-0.0.1-SNAPSHOT-jar-with-dependencies.jar tps=1
```

**TPS -> is to allow running multiple threads and create connections pool with same count.**

## Usage

Program needs settings.properties file to be available in the same directory where it is ran. This program can work in two modes. With params or no params. Mode is controlled  a property in setttings.properties.

1. With Params: Example use case would be one needs to pull all the user details based on the user ids and about 100K user ids are provided. Doing this manually would be a nightmare. In such a case a SQL can be developed to do it based on per user id or a bunch of user ids. Program takes SQL file as input and user id file as input from settings.properties file to the run the sql for each of the user ids and print the output in UTF-8 encoding.

2. No Params: This is when we need to pull lets say millions of records from database and DB client just dies while doing that. 


**setting.properties file content**

* #mode=no-params --> No param mode
* mode=with-params --> With param mode
* #SQL=select user_id from user_table where creation_date > sysdate - 365 --> Property to mention the file. Single line
* output.data.delimiter=: --> Output data delimiter. Default is coma
* connString={ConnectionString} --> Data base connection string
* username={DataBaseUsername} --> Database user name
* password={DataBasePassword} --> Database password
* sql.file.path={SQLFilePath} --> SQL file path if it is passed as file. SQL property takes precedence over sql.file.path
* InputFilePath={InputValuesFilePath} --> Input file path
