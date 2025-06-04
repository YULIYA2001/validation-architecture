package org.example.testvalidation.dto;

import jakarta.validation.constraints.*;
import org.example.testvalidation.validator.annotations.ValidDateRange;
import org.example.testvalidation.validator.utils.ValidationMessages;
import org.example.testvalidation.validator.utils.ValidationRegexps;
import org.hibernate.validator.constraints.Range;

@ValidDateRange(dateBeforeName="issueDate", dateAfterName="expiryDate")
@ValidDateRange(dateBeforeName="issueDate")
@ValidDateRange(dateAfterName="expiryDate")
public class IdentityCardDto {
    @NotNull(message = ValidationMessages.EMPTY_FIELD)
    @Range(min = 100, max=999, message = ValidationMessages.WRONG_VALUE_FROM_RANGE)
    private Integer citizenship;

    @NotBlank(message = ValidationMessages.EMPTY_FIELD)
    @Pattern(regexp = ValidationRegexps.RUSSIAN_LETTERS_REGEXP, message = ValidationMessages.FORMAT_MISMATCH_WITH_REGEXP)
    private String identityCardType;

    @Pattern(regexp = ValidationRegexps.PERSONAL_NUMBER_REGEXP, message = ValidationMessages.FORMAT_MISMATCH_WITH_REGEXP)
    private String personalNumber;

    @Pattern(regexp = ValidationRegexps.PASSPORT_SERIA_REGEXP, message = ValidationMessages.FORMAT_MISMATCH_WITH_REGEXP)
    private String seria;

    @Pattern(regexp = ValidationRegexps.PASSPORT_SERIAL_NUMBER_REGEXP, message = ValidationMessages.FORMAT_MISMATCH_WITH_REGEXP)
    private String serialNumber;

    @NotBlank(message = ValidationMessages.EMPTY_FIELD)
    private String issueDate;

    @NotBlank(message = ValidationMessages.EMPTY_FIELD)
    private String expiryDate;

    public Integer getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(Integer citizenship) {
        this.citizenship = citizenship;
    }

    public String getIdentityCardType() {
        return identityCardType;
    }

    public void setIdentityCardType(String identityCardType) {
        this.identityCardType = identityCardType;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getSeria() {
        return seria;
    }

    public void setSeria(String seria) {
        this.seria = seria;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "IdentityCardDto{" +
                "citizenship=" + citizenship +
                ", identityCardType='" + identityCardType + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", seria='" + seria + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
