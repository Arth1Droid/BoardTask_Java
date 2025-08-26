package br.com.Arth1Droid.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.Arth1Droid.dto.BoardColumnInfoDTO;
import br.com.Arth1Droid.dto.CardDetails;
import br.com.Arth1Droid.persistence.dao.CardDao;
import br.com.Arth1Droid.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;
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
            var dao = new CardDao(connection)
            var optional = dao.findById(cardId);
            CardDetails dto = optional.orElseThrow(() -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId())));

            if(dto.blocked()){
                var message = ("O card está bloqueado, é necessário desbloqueá-lo para mover".formatted(cardId));
                throw new CardBlockedException(message);
            }
    var currentColumn = boardColumnsInfo.stream()
            .filter(bc -> bc.id().equals(dto.columId()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
    if(currentColumn.kind().equals(FINAL)){
        throw new CardFinishedException("O card já foi finalizado");
    }
    var nextColumn = boardColumnInfo.stream().filter(bc -> bc.order() == currentColumn.order() + 1)
    .findFirst().orElseThrow();
    dao.moveToColumn(nextColumn.id(), cardId);
    connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }


    }
}
