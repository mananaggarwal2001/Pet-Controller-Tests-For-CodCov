package com.mananluvtocode.pet_clinic.formatters;

import com.mananluvtocode.pet_clinic.model.PetType;
import com.mananluvtocode.pet_clinic.services.PetTypeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Set;


// used for formatting the String to the respective ID type for the successfully working of the addition of the pettype.
@Component
public class PetTypeFormatter implements Formatter<PetType> {
    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        Set<PetType> findPetTypes = petTypeService.findAll();
        for (PetType type : findPetTypes) {
            if (type.getName().equals(text)) {
                return type;
            }
        }
        throw new ParseException("PetType Not Found " + text, 0);
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
