package br.com.Arth1Droid.service;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.Arth1Droid.persistence.dao.BoardColumnDAO;
import br.com.Arth1Droid.persistence.dao.BoardDAO;
import br.com.Arth1Droid.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardService {

    private final Connection connection;

    public BoardEntity insert(final BoardEntity entity) throws SQLException{
        var dao = new BoardDAO(connection);
        var BoardColumnDAO = new BoardColumnDAO(connection);
        try {
            dao.insert(entity);
            var columns = entity.getBoardColunm().stream().map( c -> {
                c.setBoard(entity);
                return c;
            }).toList();
            for (var colum : columns){
                BoardColumnDAO.insert(colum);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        return entity;
    }
    public boolean delete(final Long id)throws SQLException{
        var dao = new BoardDAO(connection);
        try{
            if(!dao.exists(id)){
                return false;
            }
            dao.delete(id);
            connection.commit();
            return true;
        }catch(SQLException e){
            connection.rollback();
            throw e;
        }
    }
}
