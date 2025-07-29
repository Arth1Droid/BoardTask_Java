package br.com.Arth1Droid.persistence.entity;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class BlockEntity {
    private long id;
    private OffsetDateTime blockedAt;
    private String BlockReason;
    private OffsetDateTime unblockedAt;
    private String unblockReason;

}
