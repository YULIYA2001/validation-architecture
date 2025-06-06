package org.example.testvalidation.validation.utils;

/**
 * Класс содержит константы с сообщениями об ошибках валидации.
 * <p>
 *     Используется для централизованного хранения текстов ошибок.
 */
public class ValidationMessages {
    public static final String EMPTY_FIELD = "Отсутствует поле.";
    public static final String FORMAT_MISMATCH = "Неправильный формат.";
    public static final String DATE_FORMAT_MISMATCH = FORMAT_MISMATCH + " Ожидается: dd.mm.yyyy с допустимыми датами.";
    public static final String FORMAT_MISMATCH_WITH_REGEXP = FORMAT_MISMATCH + " Допустимо: «{regexp}».";
    public static final String WRONG_DATE_RANGE = "Неверно указан интервал дат.";
    public static final String WRONG_VALUE_FROM_RANGE = "Недопустимое значение. Допустимо от {min} до {max}";
    public static final String NOT_FOUND_IN_DB = "Не найдено в БД";
    public static final String ALREADY_EXISTS_IN_DB = "Конфликт. Уже существует в БД";

    private ValidationMessages(){
        // utility class
    }
}
