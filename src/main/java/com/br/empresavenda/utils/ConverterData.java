package com.br.empresavenda.utils;

import lombok.experimental.UtilityClass;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
@UtilityClass
public class ConverterData {
    public static YearMonth converterDataParaMesAno (Date date) {
            var instant = date.toInstant();
            var localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            return YearMonth.from(localDate);
    }
}
