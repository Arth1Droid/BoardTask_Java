package br.com.Arth1Droid.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import br.com.Arth1Droid.persistence.dao.BoardColumnDAO;
import br.com.Arth1Droid.persistence.dao.BoardDAO;
import br.com.Arth1Droid.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardQueryService {

    private final Connection connection;

    public Optional<BoardEntity> findByid(final Long Id) throws SQLException{
        var dao = new BoardDAO(connection);
        var BoardColumnDAO = new BoardColumnDAO(connection);
        var optional = dao.findById(Id);
        if(optional.isPresent()){
            var entity = optional.get();
            entity.setBoardColunm(BoardColumnDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }
}
