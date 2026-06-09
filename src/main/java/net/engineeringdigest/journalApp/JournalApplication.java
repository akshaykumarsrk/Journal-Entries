package net.engineeringdigest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }


    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}

// PlatformTransactionManager is an interface which works for rollback and commit
// MongoTransactionManager is an implementation of PlatformTransactionManager which actually does commit and rollback
// now we need to create a bean to tell Spring that PlatformTransactionManager which is an interface which has implementation
// of MongoTransactionManager

// MongoDatabaseFactory helps us to make connection to database
// all those work that we will done, will be done in database by MongoDatabaseFactory

// when we use mongo in local and do transaction properties
// local mongo only runs single instance of mongo, replication is not here now
// and replication is mandatory in mongo for transaction to happen
// without it, it will give you an error
// Command execution failed on MongoDB server with error 20 (IllegalOperation):
// 'Transaction numbers are only allowed on a replica set member or mongos' on server
// localhost:27017. The full response is {"ok": 0.0, "errmsg": "Transaction numbers are
// only allowed on a replica set member or mongos", "code": 20, "codeName": "IllegalOperation"}

// akshaykumarmathmatics_db_user
// VzZESHrzMWezBvpO