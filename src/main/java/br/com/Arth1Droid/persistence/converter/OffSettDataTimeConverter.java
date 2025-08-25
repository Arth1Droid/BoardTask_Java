package br.com.Arth1Droid.persistence.converter;

import lombok.NoArgsConstructor;

import static java.time.ZoneOffset.UTC;
import static lombok.AccessLevel.PRIVATE;
import static java.util.Objects.nonNull;

import java.sql.Timestamp;
import java.time.OffsetDateTime;


@NoArgsConstructor(access = PRIVATE)
public final class OffSettDataTimeConverter {

    public static OffsetDateTime toOffsetDateTime(final Timestamp value){

        return nonNull(value) ? OffsetDateTime.ofInstant(value.toInstant(), UTC): null;

    }

}
