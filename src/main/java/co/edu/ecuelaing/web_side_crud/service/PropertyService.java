package co.edu.ecuelaing.web_side_crud.service;


import co.edu.ecuelaing.web_side_crud.model.Property;
import co.edu.ecuelaing.web_side_crud.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository repository;

    public Property createProperty(Property property) {
        return repository.save(property);
    }

    public List<Property> getAllProperties() {
        return repository.findAll();
    }

    public Optional<Property> getPropertyById(Long id) {
        return repository.findById(id);
    }

    public Optional<Property> updateProperty(Long id, Property newProperty) {
        return repository.findById(id).map(property -> {
            property.setAddress(newProperty.getAddress());
            property.setPrice(newProperty.getPrice());
            property.setSize(newProperty.getSize());
            property.setDescription(newProperty.getDescription());
            return repository.save(property);
        });
    }

    public boolean deleteProperty(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}