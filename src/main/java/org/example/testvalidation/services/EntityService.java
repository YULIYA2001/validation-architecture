package org.example.testvalidation.services;

import java.nio.file.AccessDeniedException;
import java.util.List;
import org.example.testvalidation.validation.AnnotationObjectValidator;
import org.example.testvalidation.validation.BusinessObjectValidator;
import org.example.testvalidation.validation.core.api.ValidationContext;
import org.springframework.stereotype.Service;

/**
 * Сервисный класс, предоставляющий операции валидации и сохранения
 */
@Service
public class EntityService {
    private final AnnotationObjectValidator annotationValidator;
    private final BusinessObjectValidator businessValidator;

    public EntityService(AnnotationObjectValidator annotationValidator, BusinessObjectValidator businessValidator) {
        this.annotationValidator = annotationValidator;
        this.businessValidator = businessValidator;
    }
    public <T> String save(T object) {
        return object.toString();
    }
    public <T> void validateByAnnotations(T object) {
        annotationValidator.validate(object);
    }
    public <T> void validateByAnnotations(List<T> objects) {
        annotationValidator.validate(objects);
    }
    public <T> void validateBusinessLogic(T object, ValidationContext context) {
        businessValidator.validate(object, context);
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
