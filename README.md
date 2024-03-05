# Calendar Application Readme

## Environment Setup
- **IDE:** Visual Studio Code (VSCode) with WSL remote
- **Operating System:** Windows 11
- **WSL Version:** Ubuntu 20.04

## Program Execution

To run the program, follow these steps:

1. Execute `UserLogin.java` located at `new_calendar/src/User/UserLogin.java`.

## Library References

Ensure the following libraries are referenced:

1. **PostgreSQL Driver:**
   - Path: `/usr/lib/jvm/java-1.8.0-openjdk-amd64/lib/postgresql-42.6.0.jar`
   
2. **JAR Files:**
   - Located in the `lib/` folder within the project directory.

## Commands

Use the following command to execute the program:

```bash
/usr/bin/env /usr/lib/jvm/java-8-openjdk-amd64/bin/java -cp /tmp/cp_6rjrwcfms8tvljxfi3rlsdjam.jar User.UserLogin
```

## Trouble shooting Display Issues

If the display doesn't show up correctly, follow these steps:

Execute the following command:

```
export DISPLAY:=0
```


## Classpath reference

```
-cp /tmp/cp_6rjrwcfms8tvljxfi3rlsdjam.jar
```

## Calendar Features 

- [x] Create a User Account  
- [x] Update a User Account  
- [x] Authenticate a User  
- [x] Calendar View  
- [x] Event Reminder Setting  
- [x] Check Availability of Family   Members
- [x] Create an Event  
- [x] Invite Family Members to the Event
- [x] View an Event  

