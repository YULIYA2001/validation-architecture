package org.example.testvalidation.validator.utils;

// all patterns here
/**
 * Класс содержит константы с регулярными выражениями для валидации.
 * <p>Используется для централизованного хранения регулярных выражений для валидации.</p>
 */
public class ValidationRegexps {
    public static final String RUSSIAN_NAME_WORD_REGEXP = "[A-ЯЁ][а-яё]*";
    public static final String RUSSIAN_LETTERS_REGEXP = "[A-ЯЁа-яё]*";
    public static final String GROUP_NUMBER_REGEXP = "^[A-Z0-9]{6}$";
    public static final String SEX_REGEXP = "^[МЖ]$";

    public static final String EMAIL_REGEXP = "^(?=.{3,30}@)[A-Za-z0-9]+([.\\-_]?[A-Za-z0-9])*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";
    public static final String PERSONAL_NUMBER_REGEXP = "^[0-9A-Z]+$";
    public static final String PASSPORT_SERIA_REGEXP = "^[0-9A-Z]{1,2}\\s?[0-9A-Z]{0,2}$";
    public static final String PASSPORT_SERIAL_NUMBER_REGEXP = "^\\d+$";
    public static final String ANY_MOBILE_PHONE_REGEXP = "^\\+\\d{11,15}$";

    /**
     * Закрытый конструктор для предотвращения создания экземпляров класса.
     */
    private ValidationRegexps(){}
}
