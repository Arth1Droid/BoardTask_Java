package br.com.Arth1Droid.ui;

import static br.com.Arth1Droid.persistence.config.ConnectionConfig.getConnection;
import static br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum.INITIAL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import br.com.Arth1Droid.dto.BoardColumnInfoDTO;
import br.com.Arth1Droid.persistence.entity.BoardColunmEntity;
import br.com.Arth1Droid.persistence.entity.BoardEntity;
import br.com.Arth1Droid.persistence.entity.CardEntity;
import br.com.Arth1Droid.service.BoardColumnQueryService;
import br.com.Arth1Droid.service.BoardQueryService;
import br.com.Arth1Droid.service.CardQueryService;
import br.com.Arth1Droid.service.CardService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardMenu {
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private final BoardEntity entity;


    public void execute() {
        try{
        System.out.printf("Bem vindo ao board %s, selecione a operação desejada:\n ", entity.getId());
        var option = -1;
        while(option != 9){
            System.out.println("1 - Criar um novo Card");
            System.out.println("2 - Mover um Card");
            System.out.println("3 - Bloquear um Card");
            System.out.println("4 - Desbloquear um Card");
            System.out.println("5 - Cancelar um Card");
            System.out.println("6 - Ver Board");
            System.out.println("7 - Ver colunas com Cards");
            System.out.println("8 - Ver Card");
            System.out.println("9 - Voltar para menu anterior um Card");
            System.out.println("10 - Sair");
            option = scanner.nextInt();
        }
        switch(option){
            case 1 -> createCard();
            case 2 -> moveCardToNextColumn();
            case 3 -> blockCard();
            case 4 -> unblockCard();
            case 5 -> cancelCard();
            case 6 -> showBoard();
            case 7 -> showColumn();
            case 8 -> showCard();
            case 9 -> System.out.println("Voltando para o menu anterior");
            case 10 -> System.exit(0);
            default -> System.out.println("Opção inválida, informe uma opção existente");
              } 
            
            }catch (SQLException ex){
                ex.printStackTrace();
                System.exit(0);
            }
        }


    private void createCard() throws SQLException {
        var card = new CardEntity();
        System.out.println("Informe o título do card: ");
        card.setTitle(scanner.next());
        System.out.println("Informe a descrição do card: ");
        card.setDescription(scanner.next());
        card.setBoardColumn(entity.getInitialColumn());
        try(var connection = getConnection()){
            new CardService(connection).insert(card);
        }
    }


    private void moveCardToNextColumn() throws SQLException {
        System.out.println("Inforeme o card que deseja mover para a próxima coluna: ");
        var cardId = scanner.nextLong();
        try(var connection = getConnection()){
            var boardColumnsInfo = entity.getBoardColunms().stream()
            .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind())).toList();
            new CardService(connection).moveToNextColumn(cardId, boardColumnsInfo);
        } catch( RuntimeException ex){
            System.out.println(ex.getMessage());
        }

    }


    private void blockCard() throws SQLException {
        System.out.println("Informe o id do card que será bloqueado: ");
        var cardId = scanner.nextLong();
        System.out.println("Informe o motivo do bloqueio: ");
        var reason = scanner.next();
        var boardColumnsInfo = entity.getBoardColunms().stream()
            .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind())).toList();
        try(var connection = getConnection()){
            new CardService(connection).block(cardId, reason,boardColumnsInfo);
        }catch( RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }


    private void unblockCard() throws SQLException {
        System.out.println("Informe o id do card que será desbloqueado: ");
        var cardId = scanner.nextLong();
        System.out.println("Informe o motivo do desbloqueio: ");
        var reason = scanner.next();  
        try(var connection = getConnection()){
            new CardService(connection).unblock(cardId, reason);
        }catch( RuntimeException ex){
            System.out.println(ex.getMessage());
        }  
    }


    private void cancelCard() throws SQLException {
        System.out.println("Informe o card que deseja mover para a coluna de cancelamento: ");
        var cardId = scanner.nextLong();
        var cancelColumn = entity.getCancelColumn();
        var boardColumnsInfo = entity.getBoardColunms().stream()
            .map(bc -> new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind())).toList();
        try(var connection = getConnection()){
            new CardService(connection).cancel(cardId, cancelColumn.getId(), boardColumnsInfo);
        } catch( RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }


    private void showBoard() throws SQLException {
        try(Connection connection = getConnection()){
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(b -> {
                System.out.printf("Board {%s, %s}\n", b.id(), b.name());
                b.columns().forEach(c -> 
                    System.out.printf("Coluna [%s] tipo: [%s] tem %s\n", c.name(), c.kind(), c.cardsAmount())
                    );
                });
            };
        }


    private void showColumn() throws SQLException {
        System.out.printf("Escolha uma coluna do Board %s\n", entity.getName());
        var columnsIds = entity.getBoardColunms().stream().map(BoardColunmEntity::getId).toList();
        var selectedColumn = -1L; 
        while(!columnsIds.contains(selectedColumn)){
            entity.getBoardColunms().forEach(c -> System.out.printf("%s - %s\n", c.getId(), c.getName(), c.getKind()));
            selectedColumn = scanner.nextLong();
        }
        try(var connection = getConnection()){
          var column =  new BoardColumnQueryService(connection).findById(selectedColumn);
          column.ifPresent(co -> {
            System.out.printf("Coluna %s tip o%s\n", co.getName(), co.getKind());
            co.getCards().forEach(ca -> System.out.printf("Card %s - %s\nDescrição: %s\n", ca.getId(), ca.getTitle(), ca.getDescription()));
          });
        }
    }


    private void showCard() throws SQLException {
        System.out.println("Informe o Id do card que deseja visualizar: ");
        var selectedCardId = scanner.nextLong();
        try(var connection = getConnection()){
            new CardQueryService(connection).findById(selectedCardId).ifPresentOrElse(c ->{
                System.out.printf("Card %s - %s\n", c.id(), c.title());
                System.out.printf("Descrição %s\n", c.description());
                System.out.println(c.blocked() ? "O card está bloqueado Motivo %s" + c.blockedReason() : " Nâo está bloqueado");
                System.out.printf("Já foi bloqueado %s vezes %s\n", c.blocksAmount());
                System.out.printf("Localizado na coluna %s - %s\n", c.columnId(), c.columnName());
        
        },
        () -> System.out.printf("Não existe card com o id %s\n ", selectedCardId)); 
        }
    }
}

