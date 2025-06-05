package org.example.testvalidation.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;

// имитация БД
@Repository
public class CommonTestRepository {
    public boolean citizenshipExistsByCode(Integer citizenship) {
        return citizenship < 500;
    }

    public boolean facultyExistsByName(String faculty) {
        return List.of("ВФ", "ФДПиПО", "ФИТУ", "ИЭФ", "ФКП", "ФКСиС", "ФКТ", "ФРЭ", "ФИБ").contains(faculty);
    }
}
