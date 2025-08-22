package br.com.Arth1Droid.dto;

import java.time.OffsetDateTime;

public record CardDetails(Long id,String title, String description, boolean blocked, OffsetDateTime blockedAt, String blockedReason, int blocksAmount, Long columnId, String columnName) {

}
