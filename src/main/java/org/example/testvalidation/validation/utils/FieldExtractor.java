package org.example.testvalidation.validation.utils;

import java.lang.reflect.Modifier;
import java.util.*;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;

public class FieldExtractor {
    public static Object findFieldByName(Object root, String fieldName) {
        return findFieldByName(root, fieldName, Collections.newSetFromMap(new IdentityHashMap<>()), "");
    }

    private static Object findFieldByName(Object obj, String fieldName, Set<Object> visited, String path) {
        if (obj == null || visited.contains(obj)) return null;
        visited.add(obj);

        for (Field field : getAllFields(obj.getClass())) {
            // Пропускаем static и synthetic поля
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) continue;

            try {
                field.setAccessible(true);
            } catch (Exception e) {
                continue; // Нельзя сделать доступным — пропускаем
            }

            Object value = ReflectionUtils.getField(field, obj);

            String currentPath = path.isEmpty() ? field.getName() : path + "." + field.getName();
            System.out.println("⮑ Проверка поля: " + currentPath);

            if (field.getName().equals(fieldName)) {
                System.out.println("✅ Найдено поле: " + currentPath + " → значение: " + value);
                return value;
            }

            // Уходим вглубь, если это объект (но не примитив или String)
            if (value != null && !isPrimitiveOrWrapper(field.getType()) && !(value instanceof String)) {
                Object nested = findFieldByName(value, fieldName, visited, currentPath);
                if (nested != null) return nested;
            }
        }

        return null;
    }

    private static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        while (type != null && type != Object.class) {
            for (Field field : type.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) && !field.isSynthetic()) {
                    fields.add(field);
                }
            }
            type = type.getSuperclass();
        }
        return fields;
    }

    private static boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive()
                || type == Boolean.class || type == Byte.class || type == Character.class
                || type == Short.class || type == Integer.class || type == Long.class
                || type == Float.class || type == Double.class;
    }
}
