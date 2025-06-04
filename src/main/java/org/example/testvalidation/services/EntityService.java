package org.example.testvalidation.services;

import java.nio.file.AccessDeniedException;
import java.util.List;
import org.example.testvalidation.validator.ObjectValidator;
import org.springframework.stereotype.Service;

@Service
public class EntityService {
    private final ObjectValidator validator;

    public EntityService(ObjectValidator validator) {
        this.validator = validator;
    }
    public <T> String save(T object) {
        return object.toString();
    }
    public <T> void validateByAnnotations(T object) {
        validator.validate(object);
    }
    public <T> void validateByAnnotations(List<T> objects) {
        validator.validate(objects);
    }

    /**
     * Метод-заглушка, имитирующий проверку прав доступа.
     * В данной реализации доступ всегда разрешён.
     *
     * @throws AccessDeniedException если пользователь не имеет необходимого уровня доступа
     *         или нарушает правила авторизации.
     */
    public void checkAccessGranted() throws AccessDeniedException {
        if (System.nanoTime() < 0) {
            throw new AccessDeniedException("Доступ запрещён: недостаточно прав.");
        }
    }
}
