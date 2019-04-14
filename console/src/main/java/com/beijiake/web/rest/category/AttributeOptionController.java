package com.beijiake.web.rest.category;


import com.beijiake.data.domain.attribute.Attribute;
import com.beijiake.data.domain.attribute.AttributeOption;
import com.beijiake.repository.attribute.AttributeOptionRepository;
import com.beijiake.repository.attribute.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
//${key:defaultValue}
@RequestMapping("/${url.api.prefix:/api}/attributes/{attributeId}/options")
public class AttributeOptionController {

    @Autowired
    AttributeRepository attributeRepository;


    @Autowired
    AttributeOptionRepository attributeOptionRepository;

    @PostMapping
    public void doPost(@PathVariable Long attributeId, @RequestBody AttributeOption attributeOption) {
        Attribute attribute = attributeRepository.findById(attributeId).orElseThrow(EntityNotFoundException::new);

        attributeOption.setAttribute(attribute);

        attributeOptionRepository.save(attributeOption);
    }

}
