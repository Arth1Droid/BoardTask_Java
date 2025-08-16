package br.com.Arth1Droid.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class BoardColunmEntity {
    private long id;
    private String name;
    private int order;
    private BoardColunmKindEnum kind;
    private BoardEntity board = new BoardEntity();
    @ToStringExclude
    @EqualsAndHashCode.Exclude
    private List <CardEntity> cards = new ArrayList<>();
}
