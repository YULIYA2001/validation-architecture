package org.example.testvalidation.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Класс имитирует работу с БД для всех объектов
 */
@Repository
public class CommonTestRepository {
    public boolean citizenshipExistsByCode(Integer citizenship) {
        return citizenship < 500;
    }

    public boolean facultyExistsByName(String faculty) {
        return List.of("ВФ", "ФДПиПО", "ФИТУ", "ИЭФ", "ФКП", "ФКСиС", "ФКТ", "ФРЭ", "ФИБ").contains(faculty);
    }

    @SuppressWarnings("all")
    public boolean personExists(String firstName, String lastName) {
        return true;
    }

    @SuppressWarnings("all")
    public boolean studentExists(String firstName, String lastName, String groupNumber) {
        return true;
    }
}
