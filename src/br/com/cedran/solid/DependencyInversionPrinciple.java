package br.com.cedran.solid;

/**
 * This principle is commonly mistaken with Dependency Injection and Inversion of Control patterns.
 *
 * DIP's definition has mainly two parts:
 * 1. High-level modules should not dependent on low-level modules. Both should depend on abstractions
 * 2. Abstractions should not depend on details. Details should depend on abstractions.
 *
 * Low-level modules usually exposes its internal data model (internal storage) and does not have business logic.
 * High-level modules allows operation to be performed on low-level modules - more directly related to business logic.
 *
 * This principle helps decoupling of modules.
 */
public class DependencyInversionPrinciple {

    /**
     * In the following example, DIP is violated, since LoginService, which is a high-level module relies on MySQLConnection
     * module that is a low-level module, while it should rely on abstractions.
     * If the DB engine changes, it would also by a violation of the OCP, since LoginService would have to change as well.
     */
    {
        class MySQLConnection {
            // ,,,
        }

        class LoginService {
            private MySQLConnection mySQLConnection;

            public LoginService(MySQLConnection mySQLConnection) {
                this.mySQLConnection = mySQLConnection;
            }
        }
    }

    /**
     * The next example, creates the abstraction which DIP refers to and avoids violation of the OCP.
     */
    interface DbConnection {
        // ...
    }


    class MySQLConnection implements DbConnection {
        // ,,,
    }

    class LoginService {
        private DbConnection dbConnection;

        public LoginService(DbConnection dbConnection) {
            this.dbConnection = dbConnection;
        }
    }

}
