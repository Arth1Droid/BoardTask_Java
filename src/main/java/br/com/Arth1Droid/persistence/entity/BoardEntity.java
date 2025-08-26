package br.com.Arth1Droid.persistence.entity;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.ToStringExclude;

import static br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum.CANCEL;
import static  br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum.INITIAL;
import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class BoardEntity {

    private Long id;
    private String name;
    @ToStringExclude
    @EqualsAndHashCode.Exclude
    private List<BoardColunmEntity> boardColunms = new ArrayList<>();

    public BoardColunmEntity getInitialColumn() {
            return getFilteredColumn(bc -> bc.getKind().equals(INITIAL));
    }

    public BoardColunmEntity getCancelColumn(){
        return getFilteredColumn(bc -> bc.getKind().equals(CANCEL));
    }

    private BoardColunmEntity getFilteredColumn(Predicate<BoardColunmEntity> filter){
            return boardColunms.stream()
             .filter(filter)
             .findFirst().orElseThrow();  
    }
}
