package org.example.testvalidation.validation.utils;

import java.lang.reflect.Modifier;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;

/**
 * Служебный класс для рекурсивного поиска значения поля по имени в объекте и его вложенных объектах
 * <p>
 * Позволяет безопасно обходить объекты, включая наследование, избегая циклических ссылок.
 * <p>
 * Игнорирует статические и синтетические поля, а также поля, к которым невозможно получить доступ.
 */
public class FieldExtractor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldExtractor.class);
    private static final Object UNAVAILABLE = new Object();

    private FieldExtractor(){
        // utility class
    }

    /**
     * Ищет значение поля с заданным именем в объекте и его вложенных объектах рекурсивно
     *
     * @param root корневой объект, в котором начинается поиск
     * @param fieldName имя поля, которое нужно найти
     * @return значение найденного поля, либо {@code null}, если поле не найдено
     */
    public static Object findFieldByName(Object root, String fieldName) {
        return findFieldByName(root, fieldName, Collections.newSetFromMap(new IdentityHashMap<>()));
    }

    /**
     * Рекурсивно ищет значение поля с заданным именем в объекте и его вложенных объектах
     * <p>
     * Использует множество {@code visited} для отслеживания посещённых объектов и предотвращения зацикливания.
     *
     * @param obj объект, в котором производится поиск
     * @param fieldName имя поля, значение которого необходимо найти
     * @param visited множество посещённых объектов для предотвращения зацикливания
     * @return значение поля с именем {@code fieldName}, если оно найдено, иначе {@code null}
     */
    private static Object findFieldByName(Object obj, String fieldName, Set<Object> visited) {
        if (obj == null || visited.contains(obj)) return null;
        visited.add(obj);

        for (Field field : getAllFields(obj.getClass())) {
            if (!trySetAccessible(field)) continue;

            Object value = getFieldValue(field, obj);
            if (value == UNAVAILABLE) continue;

            if (field.getName().equals(fieldName)) {
                return value;
            }

            if (shouldRecurse(field.getType(), value)) {
                Object nested = findFieldByName(value, fieldName, visited);
                if (nested != null) {
                    return nested;
                }
            }
        }

        return null;
    }

    /**
     * Возвращает список всех полей класса и его суперклассов, исключая статические и синтетические поля
     * Использует {@link ReflectionUtils#doWithFields} для обхода и сбора полей.
     *
     * @param type класс, для которого необходимо получить все поля
     * @return список всех нестатических и не синтетических полей класса и всех его суперклассов
     */
    private static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        ReflectionUtils.doWithFields(type, fields::add, field ->
                !Modifier.isStatic(field.getModifiers()) && !field.isSynthetic()
        );
        return fields;
    }

    /**
     * Пытается установить доступность для указанного поля
     *
     * @param field поле, для которого нужно установить доступность
     * @return {@code true}, если доступность установлена; {@code false}, если произошла ошибка
     */
    private static boolean trySetAccessible(Field field) {
        try {
            field.setAccessible(true);
            return true;
        } catch (Exception e) {
            LOGGER.debug("Cannot make field accessible: {}", field.getName(), e);
            return false;
        }
    }

    /**
     * Получает значение указанного поля из объекта
     * <p>
     * Если доступ к полю не может быть получен из-за IllegalArgumentException, логирует это и возвращает
     * специальный маркер {@code UNAVAILABLE}.
     *
     * @param field поле, значение которого нужно получить
     * @param obj объект, из которого извлекается значение поля
     * @return значение поля, либо {@code UNAVAILABLE}, если получить значение не удалось
     */
    private static Object getFieldValue(Field field, Object obj) {
        try {
            return ReflectionUtils.getField(field, obj);
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Failed to read field '{}'", field.getName(), e);
            return UNAVAILABLE;
        }
    }

    /**
     * Определяет, нужно ли рекурсивно обрабатывать поле с данным типом и значением
     * Рекурсия выполняется, если значение не null и тип сложный.
     *
     * @param type  класс типа значения поля
     * @param value значение поля
     * @return {@code true}, если нужно рекурсивно обрабатывать поле, {@code false} — иначе
     */
    private static boolean shouldRecurse(Class<?> type, Object value) {
        return value != null && isComplexType(type);
    }

    /**
     * Проверяет, сложный ли тип: ни примитив, ни обертка, ни строка, ни enum
     *
     * @param type класс для проверки
     * @return {@code true}, если тип сложный, {@code false} — если примитивный, обертка, строка или enum
     */
    private static boolean isComplexType(Class<?> type) {
        return !ClassUtils.isPrimitiveOrWrapper(type)
                && !String.class.equals(type)
                && !type.isEnum();
    }
}
