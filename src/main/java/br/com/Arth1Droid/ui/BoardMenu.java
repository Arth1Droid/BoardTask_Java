package br.com.Arth1Droid.ui;

import static br.com.Arth1Droid.persistence.config.ConnectionConfig.getConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import br.com.Arth1Droid.persistence.entity.BoardColunmEntity;
import br.com.Arth1Droid.persistence.entity.BoardEntity;
import br.com.Arth1Droid.service.BoardColumnQueryService;
import br.com.Arth1Droid.service.BoardQueryService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardMenu {
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private final BoardEntity entity;


    public void execute() {
        try{
        System.out.printf("Bem vindo ao board %s, selecione a operação desejada: ", entity.getId());
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


    private void createCard() {
  
    }


    private void moveCardToNextColumn() {

    }


    private void blockCard() {
   
    }


    private void unblockCard() {
     
    }


    private void cancelCard() {
       
    }


    private void showBoard() throws SQLException {
        try(Connection connection = getConnection()){
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(b ->{
                System.out.printf("Board {%s}\n", b.id(), b.name());
                b.columns().forEach(c -> 
                    System.out.printf("Coluna [%s] tipo: [%s] tem %s\n" c.name(), c.kind(), c.cardsAmount())
                    );
                });
            };
        }


    private void showColumn() throws SQLException {
        System.out.printf("Escolha uma coluna do Board %s\n", entity.getName());
        var columnsIds = entity.getBoardColunm().stream().map(BoardColunmEntity::getId).toList();
        var selectedColumn = -1L; 
        while(!columnsIds.contains(selectedColumn)){
            entity.getBoardColunm().forEach(c -> System.out.printf("%s - %s\n", c.getId(), c.getName(), c.getKind()));
            selectedColumn = scanner.nextLong();
        }
        try(var connection = getConnection()){
          var column =  new BoardColumnQueryService(connection).findById(selectedColumn);
          column.ifPresent(co -> {
            System.out.printf("Coluna %s tip o%s\n", co.getName(), co.getKind());
            co.getCards().forEach(ca -> System.out.printf("Card %s - %s\nDescrição: %s", ca.getId(), ca.getTitle(), ca.getDescription()));
          });
        }
    }


    private void showCard() {
        
    }
}

