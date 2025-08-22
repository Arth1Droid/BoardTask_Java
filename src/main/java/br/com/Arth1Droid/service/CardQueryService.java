package br.com.Arth1Droid.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import br.com.Arth1Droid.dto.CardDetails;
import br.com.Arth1Droid.persistence.dao.CardDao;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardQueryService {

    private final Connection connection;

    public Optional <CardDetails> findById(final Long id) throws SQLException{
        var dao = new CardDao(connection);
        return dao.findById(id);
    }

    

}
