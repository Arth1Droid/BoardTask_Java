package br.com.Arth1Droid.service;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.Arth1Droid.persistence.dao.CardDao;
import br.com.Arth1Droid.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardService {
    private final Connection connection;

    public CardEntity insert( final CardEntity entity ) throws SQLException{
        try {
            var dao = new CardDao(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
}
