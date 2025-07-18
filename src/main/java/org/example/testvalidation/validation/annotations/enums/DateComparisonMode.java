package org.example.testvalidation.validation.annotations.enums;

/**
 * Режим сравнения дат
 */
public enum DateComparisonMode {
    /**
     * Нестрогое сравнение (&lt;= или &gt;=)
     */
    INCLUSIVE("не позднее", "не ранее"),

    /**
     * Строгое сравнение (&lt; или &gt;)
     */
    EXCLUSIVE("до", "после");

    private final String beforeMsg;
    private final String afterMsg;

    DateComparisonMode(String beforeMsg, String afterMsg) {
        this.beforeMsg = beforeMsg;
        this.afterMsg = afterMsg;
    }

    public String getBeforeMsg() {
        return beforeMsg;
    }

    public String getAfterMsg() {
        return afterMsg;
    }
}
