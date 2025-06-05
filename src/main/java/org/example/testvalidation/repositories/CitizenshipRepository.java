package org.example.testvalidation.repositories;

import org.springframework.stereotype.Repository;

// имитация БД
@Repository
public class CitizenshipRepository {
    public boolean existsByCode(Integer citizenship) {
        return citizenship < 500;
    }
}
