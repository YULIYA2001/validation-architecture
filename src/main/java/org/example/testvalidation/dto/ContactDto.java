package org.example.testvalidation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.testvalidation.validation.annotations.ContactTypeValueMatch;
import org.example.testvalidation.validation.utils.ValidationMessages;

@JsonPropertyOrder({ "typeId", "value" })
@ContactTypeValueMatch
public class ContactDto {
    @NotNull(message = ValidationMessages.EMPTY_FIELD)
    private Long typeId;

    @NotBlank(message = ValidationMessages.EMPTY_FIELD)
    private String value;

    public ContactDto() {
        // for DTO mapping with Jackson library
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ContactDto{" +
                "typeId=" + typeId +
                ", value='" + value + '\'' +
                '}';
    }
}
