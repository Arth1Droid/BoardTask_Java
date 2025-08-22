package br.com.Arth1Droid.persistence.converter;

import lombok.NoArgsConstructor;

import static java.time.ZoneOffset.UTC;
import static lombok.AccessLevel.PRIVATE;

import java.sql.Timestamp;
import java.time.OffsetDateTime;


@NoArgsConstructor(access = PRIVATE)
public final class OffSettDataTimeConverter {

    public static OffsetDateTime toOffsetDateTime(final Timestamp value){

        return OffsetDateTime.ofInstant(value.toInstant(), UTC);

    }

}
