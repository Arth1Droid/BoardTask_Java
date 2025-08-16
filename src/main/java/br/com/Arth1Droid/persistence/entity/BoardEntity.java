package br.com.Arth1Droid.persistence.entity;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class BoardEntity {

    private long id;
    private String name;
     @ToStringExclude
    @EqualsAndHashCode.Exclude
    private List<BoardColunmEntity> boardColunm = new ArrayList<>();
}
