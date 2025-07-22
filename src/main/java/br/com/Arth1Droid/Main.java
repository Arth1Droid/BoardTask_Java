package br.com.Arth1Droid;

import br.com.Arth1Droid.persistence.migration.MigrationStrategy;
import static br.com.Arth1Droid.persistence.config.ConnectionConfig.getConnection;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()){
            new  MigrationStrategy(connection).executeMigration();
        }
    }
}
