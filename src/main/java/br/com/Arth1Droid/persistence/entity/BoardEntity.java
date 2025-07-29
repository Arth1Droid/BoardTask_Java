package br.com.Arth1Droid.persistence.entity;

import java.util.List;
import java.util.ArrayList;

import lombok.Data;

@Data
public class BoardEntity {

    private long id;
    private String name;
    private List<BoardColunmEntity> boardColunm = new ArrayList<>();
}
