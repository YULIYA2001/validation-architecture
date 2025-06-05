package org.example.testvalidation.repositories;

import org.springframework.stereotype.Repository;

// имитация БД
@Repository
public class CommonTestRepository {
    public boolean citizenshipExistsByCode(Integer citizenship) {
        return citizenship < 500;
    }
}
