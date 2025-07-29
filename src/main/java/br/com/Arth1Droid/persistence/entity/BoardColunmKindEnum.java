package br.com.Arth1Droid.persistence.entity;

import java.util.stream.Stream;

public enum BoardColunmKindEnum {
    INITIAL, FINAL, CANCEL, PENDING;

    public static BoardColunmKindEnum findByName(final String name){
        return Stream.of(BoardColunmKindEnum.values())
        .filter(b -> b.name().equals(name))
        .findFirst().orElseThrow();

    }
}
