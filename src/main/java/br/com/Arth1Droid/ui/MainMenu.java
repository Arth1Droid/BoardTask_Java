package br.com.Arth1Droid.ui;

import static br.com.Arth1Droid.persistence.config.ConnectionConfig.getConnection;
import static br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum.CANCEL;
import static br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum.FINAL;
import static br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum.INITIAL;
import static br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum.PENDING;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.Arth1Droid.persistence.entity.BoardColunmEntity;
import br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum;
import br.com.Arth1Droid.persistence.entity.BoardEntity;
import br.com.Arth1Droid.service.BoardQueryService;
import br.com.Arth1Droid.service.BoardService;

public class MainMenu {
    private final Scanner scanner = new  Scanner(System.in);

    public void execute() throws SQLException{
        System.out.println("Bem vindo ao Gerenciador de Boards, escolha uma opção");
        var option = -1;
        while(true){
            System.out.println(" 1 - Criar um novo Board");
            System.out.println(" 2 - Selecionar um Board existente");
            System.out.println(" 3 - Excluir um Board");
            System.out.println(" 4 - Sair");
            option = scanner.nextInt();
            switch (option) {
               
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    private void createBoard() throws SQLException {
        var entity = new BoardEntity();
        System.out.println("informe o nome do seu Board");
        entity.setName(scanner.next());

        System.out.println("Seu Board terá colunas além das três padrões ? Se sim digite a quantidade / Senão digite 0 ");
        var additionalColumns = scanner.nextInt();

        List<BoardColunmEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do Board: ");
        var initialColumName = scanner.next();
        var initialColum = createColum(initialColumName, INITIAL, 0);
        columns.add(initialColum);

        for (int i = 0; i < additionalColumns; i++) {
        System.out.println("Informe o nome da coluna de tarefa pendente: ");
        var pendingColumName = scanner.next();
        var pendingColum = createColum(pendingColumName, PENDING,  additionalColumns + 1);
        columns.add(initialColum);         
            
        }
        
        System.out.println("Informe o nome da coluna final: ");
        var finalColumName = scanner.next();
        var finalColum = createColum(finalColumName, FINAL,  additionalColumns + 1);
        columns.add(initialColum);   

        System.out.println("Informe o nome da coluna de cancelamento do board: ");
        var cancelColumName = scanner.next();
        var cancelColum = createColum(cancelColumName, CANCEL,  additionalColumns + 1);
        columns.add(cancelColum); 

        entity.setBoardColunm(columns);
        try(var connection = getConnection()){
            var service = new BoardService(connection);
            service.insert(entity);
        }
    }


    private void selectBoard() throws SQLException {
        System.out.println("Informe o id do Board que deseja selecionar: ");
        var id = scanner.nextLong();
        try(var connection= getConnection()){
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findByid(id);
            optional.ifPresentOrElse(b -> new BoardMenu(optional.get()).execute(), () -> System.out.printf("Não foi encontrado um board com o id %s\n", id));

        }
    }

    private void deleteBoard() throws SQLException {
        System.out.println("Informe o id do Board que será excluido: ");
        var id = scanner.nextLong();
        try(var connection = getConnection()){
            var service = new BoardService(connection);
            if(service.delete(id)){
                System.out.printf("O Board %s foi excluido\n", id);
            } else{
                System.out.printf("Não foi encontrado um Board com o Id %s\n", id);

            }

        }

    }

    private BoardColunmEntity createColum(final String name, final BoardColunmKindEnum kind, final int order){
        var boardColunm = new BoardColunmEntity();
        boardColunm.setName(name);
        boardColunm.setKind(kind);
        boardColunm.setOrder(order);
        return boardColunm;
    }

}
