package com.beijiake.api_server.rest.category.secured;


import com.beijiake.api_server.service.category.CategoryService;
import com.beijiake.data.domain.attribute.AttributeOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
//${key:defaultValue}
public class AttributeOptionController {

    @Autowired
    CategoryService categoryService;


    @PostMapping("${url-patterns.secured:/api/secured}/attributes/{attributeId}/options")
    public AttributeOption doPost(@PathVariable Long attributeId, @RequestBody AttributeOption attributeOption) {
        return categoryService.addAttributeOption(attributeId, attributeOption);
    }

    @DeleteMapping("${url-patterns.secured:/api/secured}/attributeOptions/{attributeOptionId}")
    public void doDelete(@PathVariable Long attributeOptionId) {
        categoryService.deleteAttributeOption(attributeOptionId);
    }

}
