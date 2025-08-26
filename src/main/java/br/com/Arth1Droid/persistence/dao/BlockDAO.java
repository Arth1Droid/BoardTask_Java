package br.com.Arth1Droid.persistence.dao;

import static br.com.Arth1Droid.persistence.converter.OffSettDataTimeConverter.toTimestamp;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BlockDAO {
    private final Connection connection;

    public void block(final String reason , final Long cardId) throws SQLException{
        var sql = "INSERT INTO BLOCKS (blocked_at, blocked_reason, card_id) VALUES (?, ?, ?);";
        try(var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setTimestamp( i ++, toTimestamp(OffsetDateTime.now())); 
            statement.setString( i ++, reason); 
            statement.setLong(i, cardId);
            statement.executeUpdate(); 

        }
    }
}
