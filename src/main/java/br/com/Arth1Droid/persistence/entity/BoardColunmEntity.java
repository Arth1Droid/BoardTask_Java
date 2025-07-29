package br.com.Arth1Droid.persistence.entity;

import lombok.Data;

@Data
public class BoardColunmEntity {
    private long id;
    private String name;
    private int order;
    private BoardColunmKindEnum kind;
    private BoardEntity board = new BoardEntity();
}
