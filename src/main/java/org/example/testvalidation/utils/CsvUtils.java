package org.example.testvalidation.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.example.testvalidation.dto.ContactDto;
import org.example.testvalidation.exceptions.CsvProcessingException;


/**
 * Утилитный класс для работы с CSV-файлами с использованием Jackson CsvMapper
 * <p>
 * Позволяет считывать объекты из CSV. Предполагается использование моделей с аннотациями Jackson
 * для корректного маппинга. Например, {@code @JsonPropertyOrder({ "typeId", "value" })} в
 * {@link ContactDto}
 */
public class CsvUtils {
    private CsvUtils() {}

    /**
     * Считывает список объектов из CSV-потока.
     *
     * @param inputStream входящий поток CSV-данных
     * @param clazz       класс объекта, в который нужно преобразовать строки CSV
     * @param separator   символ-разделитель колонок (например, ';' или ',')
     * @param useHeader   использовать ли первую строку как заголовок
     * @return список объектов, прочитанных из CSV
     */
    public static <T> List<T> readCsv(InputStream inputStream, Class<T> clazz, char separator, boolean useHeader) {
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = mapper.schemaFor(clazz)
                    .withColumnSeparator(separator)
                    .withUseHeader(useHeader);

            ObjectReader reader = mapper.readerFor(clazz).with(schema);
            MappingIterator<T> iterator = reader.readValues(inputStream);
            return iterator.readAll();
        } catch (IOException e) {
            throw new CsvProcessingException("Ошибка обработки CSV", e);
        }
    }
}
