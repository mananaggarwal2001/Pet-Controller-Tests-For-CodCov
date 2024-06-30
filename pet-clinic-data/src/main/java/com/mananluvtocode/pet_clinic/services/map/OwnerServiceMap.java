package com.mananluvtocode.pet_clinic.services.map;

import com.mananluvtocode.pet_clinic.model.Owner;
import com.mananluvtocode.pet_clinic.model.Pet;
import com.mananluvtocode.pet_clinic.model.PetType;
import com.mananluvtocode.pet_clinic.services.CrudService;
import com.mananluvtocode.pet_clinic.services.OwnerService;
import com.mananluvtocode.pet_clinic.services.PetService;
import com.mananluvtocode.pet_clinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile("map")
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {
    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner save(Owner object) {
        Owner savedOwner = null;
        if (object != null) {
            if (object.getPets() != null) {
                object.getPets().forEach(pet -> {
                    if (pet.getPetType() != null) {
                        if (pet.getPetType().getId() == null) {
                            PetType resultantPetType = petTypeService.save(pet.getPetType());
                            pet.setPetType(resultantPetType);
                        }
                        if (pet.getId() == null) {
                            Pet resultantPet = petService.save(pet);
                            pet.setId(resultantPet.getId());
                        }
                    } else {
                        throw new RuntimeException("Pet Type is Required");
                    }
                });
            }
            return super.save(object);
        } else {
            return null;
        }
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}
