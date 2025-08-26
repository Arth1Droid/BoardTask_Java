package br.com.Arth1Droid.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.Arth1Droid.dto.BoardColumnInfoDTO;
import br.com.Arth1Droid.dto.CardDetails;
import br.com.Arth1Droid.exception.CardBlockedException;
import br.com.Arth1Droid.exception.CardFinishedException;
import br.com.Arth1Droid.exception.EntityNotFoundException;
import br.com.Arth1Droid.persistence.dao.BlockDAO;
import br.com.Arth1Droid.persistence.dao.CardDao;
import br.com.Arth1Droid.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import static br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum.CANCEL;
import static br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum.FINAL;

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

    public void moveToNextColumn(final Long cardId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException{
        try {
            var dao = new CardDao(connection);
            var optional = dao.findById(cardId);
            CardDetails dto = optional.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId)));

            if(dto.blocked()){
                var message = ("O card está bloqueado, é necessário desbloqueá-lo para mover".formatted(cardId));
                throw new CardBlockedException(message);
            }
    var currentColumn = boardColumnsInfo.stream()
            .filter(bc -> bc.id().equals(dto.columnId()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
    if(currentColumn.kind().equals(FINAL)){
        throw new CardFinishedException("O card já foi finalizado");
    }
    var nextColumn = boardColumnsInfo.stream().filter(bc -> bc.order() == currentColumn.order() + 1)
    .findFirst().orElseThrow(()-> new IllegalStateException("O card está cancelado"));
    dao.moveToColumn(nextColumn.id(), cardId);
    connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }


    }

    public void cancel(final Long cardId, final Long cancelColumnId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException{
        try {
            var dao = new CardDao(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(
                () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId)));
                if(dto.blocked()){
                    var message = "O card %s já está bloqueado".formatted(cardId);
                    throw new CardBlockedException(message);
                }
            var currentColumn = boardColumnsInfo.stream()
            .filter(bc -> bc.id().equals(dto.columnId()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
    if(currentColumn.kind().equals(FINAL)){
        throw new CardFinishedException("O card já foi finalizado");
    }
            
    boardColumnsInfo.stream().filter(bc -> bc.order() == currentColumn.order() + 1)
    .findFirst().orElseThrow(()-> new IllegalStateException("O card está cancelado"));
            dao.moveToColumn(cancelColumnId, cardId);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }

    }

    public void block(final Long id, final String reason, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException{
       try {
        var dao = new CardDao(connection);
        var optional = dao.findById(id);
        CardDetails dto = optional.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id))
        );
            if(dto.blocked()){
                var message = ("O card %s já está bloqueado".formatted(id));
                throw new CardBlockedException(message);
            }
            var currentColumn = boardColumnsInfo.stream()
            .filter(bc -> bc.id().equals(dto.columnId()))
            .findFirst().orElseThrow();

            if(currentColumn.kind().equals(FINAL) || currentColumn.kind().equals(CANCEL)){
                var message = "O card está em uma coluna do tipo %s e não pode ser  bloqueado ".formatted(currentColumn.kind());
                throw new IllegalStateException(message);
            }
        var blockDAO = new BlockDAO(connection);
        blockDAO.block(reason, id);
        connection.commit();

       } catch (SQLException ex) {
        connection.rollback();
        throw ex;
       }
    }

    public void unblock(final Long id, String reason) throws SQLException{
        try {
        var dao = new CardDao(connection);
        var optional = dao.findById(id);
        CardDetails dto = optional.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id))
        );
            if(!dto.blocked()){
                var message = ("O card %s não está bloqueado".formatted(id));
                throw new CardBlockedException(message);
            }            
            var blockDAO = new BlockDAO(connection);
            blockDAO.unblock(reason, id);
            connection.commit();        
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
    
 
}
