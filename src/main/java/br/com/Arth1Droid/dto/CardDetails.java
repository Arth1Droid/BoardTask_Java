package br.com.Arth1Droid.dto;

import java.time.OffsetDateTime;

public record CardDetails(Long id, boolean blocked, OffsetDateTime blockedAt, String blockedReason, int blocksAmount, Long columId, String columName) {

}
