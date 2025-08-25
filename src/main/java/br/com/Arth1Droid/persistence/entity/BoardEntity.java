package br.com.Arth1Droid.persistence.entity;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;
import static  br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum.INITIAL;
import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class BoardEntity {

    private long id;
    private String name;
     @ToStringExclude
    @EqualsAndHashCode.Exclude
    private List<BoardColunmEntity> boardColunms = new ArrayList<>();

        public BoardColunmEntity getInitial(){
        return boardColunms.stream().filter(bc -> bc.getKind().equals(INITIAL)).findFirst().orElseThrow(null);
        
    }

        public BoardColunmEntity getInitialColumn() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getInitialColumn'");
        }
}
