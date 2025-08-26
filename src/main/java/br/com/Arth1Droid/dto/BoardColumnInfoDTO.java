package br.com.Arth1Droid.dto;

import br.com.Arth1Droid.persistence.entity.BoardColunmKindEnum;

public record BoardColumnInfoDTO(Long id, int order, BoardColunmKindEnum kind) {
} 


