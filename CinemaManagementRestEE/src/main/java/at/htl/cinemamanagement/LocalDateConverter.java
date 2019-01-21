package at.htl.cinemamanagement;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String s) throws Exception {
        return LocalDate.parse(s, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String marshal(LocalDate localDateTime) throws Exception {
        return localDateTime.format(DateTimeFormatter.ISO_DATE);
    }
}
