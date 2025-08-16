package br.com.Arth1Droid.persistence.dao;

import java.sql.Connection;

import br.com.Arth1Droid.dto.CardDetails;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardDao {

    private Connection connection;

    public CardDetails findById(final Long id){
        return null;
    }
}
