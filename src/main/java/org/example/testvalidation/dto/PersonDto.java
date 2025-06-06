package org.example.testvalidation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import org.example.testvalidation.validation.annotations.ValidDateRange;
import org.example.testvalidation.validation.annotations.enums.DateComparisonMode;
import org.example.testvalidation.validation.utils.ValidationMessages;
import org.example.testvalidation.validation.utils.ValidationRegexps;

@ValidDateRange(dateBeforeName="birthDate", strictness=DateComparisonMode.EXCLUSIVE)
public class PersonDto {
    @NotBlank(message = ValidationMessages.EMPTY_FIELD)
    @Pattern(regexp = ValidationRegexps.RUSSIAN_NAME_WORD_REGEXP, message = ValidationMessages.FORMAT_MISMATCH_WITH_REGEXP)
    private String lastName;

    @NotBlank(message = ValidationMessages.EMPTY_FIELD)
    @Pattern(regexp = ValidationRegexps.RUSSIAN_NAME_WORD_REGEXP, message = ValidationMessages.FORMAT_MISMATCH_WITH_REGEXP)
    private String firstName;

    @Pattern(regexp = ValidationRegexps.RUSSIAN_NAME_WORD_REGEXP, message = ValidationMessages.FORMAT_MISMATCH_WITH_REGEXP)
    private String middleName;

    @NotBlank(message = ValidationMessages.EMPTY_FIELD)
    @Pattern(regexp = ValidationRegexps.SEX_REGEXP, message = ValidationMessages.FORMAT_MISMATCH_WITH_REGEXP)
    private String sex;

    private String birthDate;

    // Возможно, лучше разделить на mobile и email. Здесь просто демонстрация работы с массивом
    @NotEmpty(message = ValidationMessages.EMPTY_FIELD)
    private final List<@NotNull @Valid ContactDto> contacts = new ArrayList<>();

    @Valid
    @NotNull(message = ValidationMessages.EMPTY_FIELD)
    private IdentityCardDto identityCard;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<ContactDto> getContacts() {
        return contacts;
    }

    public IdentityCardDto getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(IdentityCardDto identityCard) {
        this.identityCard = identityCard;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "identityCard=" + identityCard +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", sex='" + sex + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
