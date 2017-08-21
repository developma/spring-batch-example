# What's the batch-example?

The batch-example is a batch application using Spring Batch and MyBatis.  

The batch-example is showing a reference implementation that calculate a bonus of employee.

# Specification

There are three tables.
- First table, GRADE table is for storing a grade of employee.  
- Second table, EMP table is for storing employee information.  
- Third, BONUS table is for storing a bonus of employee.  

The batch-example reads employee information from EMP table,  
and calculates a bonus based on that data and GRADE table,  
and stores a bonus data to BONUS table.  

# Requirement

* Gradle
* PostgreSQL 9.5 later

# How to run

* Run BatchExampleApplication.
* ./gradlew bootRun -Pargs="-job multiJob" on CUI.
