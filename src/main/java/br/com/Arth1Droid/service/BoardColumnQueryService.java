package br.com.Arth1Droid.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import br.com.Arth1Droid.persistence.dao.BoardColumnDAO;
import br.com.Arth1Droid.persistence.entity.BoardColunmEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardColumnQueryService {

    private final Connection connection;

    public Optional<BoardColunmEntity> findById(final Long id) throws SQLException{
        var dao = new BoardColumnDAO(connection);
        return dao.findById(id);

    }
}
