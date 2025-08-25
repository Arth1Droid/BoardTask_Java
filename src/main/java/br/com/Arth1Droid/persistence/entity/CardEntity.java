package br.com.Arth1Droid.persistence.entity;
import static br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum.INITIAL;
import lombok.Data;

@Data
public class CardEntity {
    private long id;
    private String title;
    private String description;
    private BoardColunmEntity boardColumn = new BoardColunmEntity();


}
