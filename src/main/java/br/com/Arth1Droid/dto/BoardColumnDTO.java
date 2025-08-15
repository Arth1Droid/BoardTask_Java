package br.com.Arth1Droid.dto;

import br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum;

public record BoardColumnDTO(Long id, String name, BoardColunmKindEnum kind , int cards_amount) {

}
