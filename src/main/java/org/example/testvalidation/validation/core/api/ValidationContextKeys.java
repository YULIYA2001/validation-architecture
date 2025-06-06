package org.example.testvalidation.validation.core.api;

import org.example.testvalidation.validation.validators.CitizenshipExistsConditionalValidator;

/**
 * Перечисление ключей контекста валидации
 * <p>
 *     Значения данного перечисления используются в {@link ValidationContext}, чтобы определить,
 *     какие валидаторы следует активировать при проверке объекта.
 * <p>
 *     Каждый ключ связан с конкретным именем поля в DTO-объекте.
 */
public enum ValidationContextKeys {
    /**
     * Ключ, активирующий бизнес-валидацию наличия гражданства для поля с именем {@code "citizenship"}.
     * <p>
     *     Используется, например, в {@link CitizenshipExistsConditionalValidator}
     */
    CITIZENSHIP_EXISTS("citizenship"),
    FACULTY_EXISTS("faculty"),
    STUDENT(""),

    //TODO оооочень важный комментарий (не успела сделать адекватнее)
    // !!! неиспользуемые ключи контекста: LAST_NAME, FIRST_NAME, GROUP_NUMBER из этой энамки нужно удалить.
    // Эти ключи не используется непосредственно для задания контекста валидатора:
    // нет валлидатора, в котором:
    //    public Set<ValidationContextKeys> getKeys() {
    //        return Set.of(ValidationContextKeys.LAST_NAME);
    //    }
    // Они используются только чтобы достать название поля dto: ValidationContextKeys.LAST_NAME.getFieldName()
    // поэтому  можно было сразу писать строку "lastName" вместо ValidationContextKeys.LAST_NAME.getFieldName(),
    // но тогда будет дублирование строк и при переименовании станет сложно ничего не упустить.
    // Один из вариантов решения - вынести в отдельный класс названия dto в виде статических полей или другой энамки,
    // эти поля использовать непосредственно в валидаторах и в конструкторах этой энамки, тогда в энамке не будет
    // лишних ключей контекста
    LAST_NAME("lastName"),
    FIRST_NAME("firstName"),
    GROUP_NUMBER("groupNumber");

    private final String fieldName;

    ValidationContextKeys(final String id) {
        this.fieldName = id;
    }

    public String getFieldName() {
        return fieldName;
    }
}
