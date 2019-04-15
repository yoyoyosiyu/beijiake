package com.beijiake.api_server.service.category;

import com.beijiake.api_server.exception.IntegrityConstraintViolationException;
import com.beijiake.api_server.exception.ReferenceViolationException;
import com.beijiake.data.domain.attribute.Attribute;
import com.beijiake.data.domain.attribute.AttributeOption;
import com.beijiake.data.domain.category.Category;
import com.beijiake.repository.attribute.AttributeOptionRepository;
import com.beijiake.repository.attribute.AttributeRepository;

import com.beijiake.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    AttributeOptionRepository attributeOptionRepository;


    /**
     * 添加分类
     * @param parentId 如果parentId为0，那么添加最顶层分类
     * @param categories
     * @return
     */
    public List<Category> addCategories(Long parentId, List<Category> categories) {

        if (categories.isEmpty()) return Collections.emptyList();

        Category parent = null;

        if (parentId> 0)
            parent = categoryRepository.findById(parentId).orElseThrow(EntityNotFoundException::new);

        List<Category> results = new ArrayList<>();

        for(Category category: categories) {
            category.setParent(parent);
            category.setChildren(Collections.emptySet());
            results.add(categoryRepository.save(category));
        }

        return results;
    }

    /**
     * 删除分类
     * @param id 需要删除的分类ID
     * @param recurse 如果设置为是，那么在分类具有子分类的时候，会迭代的先删除子分类。如果设置为否，那么如果有子分类，那么删除会失败
     */

    public void deleteCategory(Long id, boolean recurse) {

        Category category =  categoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("分类ID：%d对应的分类不存在", id)));

        Set<Category> children = category.getChildren();

        if (children == null || children.isEmpty())
        {
            categoryRepository.delete(category);
            return;
        }

        if (recurse == false) {
            throw new ReferenceViolationException("分类下面仍然包含有子分类，在删除完所有子分类之前不能删除该分类");
        }

        children.forEach((child) -> {
            deleteCategory(child.getId(), recurse);
        });

        categoryRepository.delete(category);
    }


    /**
     * 获取分类的属性
     * @param Category
     * @param bGetAncestors 是否获取分类的祖先分类的属性
     * @return
     *
     * 如果指定了获取祖先分类的属性，那么该方法会逐层往上获取父分类的属性，直到某个祖先分类的是否继承属性设置为否。
     * 如果父类存在相同的属性，那么子类的属性会优先于父类。
     */

    public List<Attribute> getCategoryAttributes(Category Category, boolean bGetAncestors) {
        List<Attribute> attributes = attributeRepository.findAttributesByCategory(Category);

        if (Category.isInheritAttribute() && Category.getParent() != null && bGetAncestors == true) {
            List<Attribute> parentAttributes = getCategoryAttributes(Category.getParent(), bGetAncestors);

            for (Attribute attribute: parentAttributes) {
                if (!this.AttributesContains(attributes, attribute)) {
                    attributes.add(attribute);
                }
            }
        }

        return attributes;
    }

    /**
     * 获取分类的属性
     * @param categoryId
     * @param bGetAncestors 参考重载的
     * @return
     */
    public List<Attribute> getCategoryAttributes(Long categoryId, boolean bGetAncestors) {

        Category Category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);

        return getCategoryAttributes(Category, bGetAncestors);

    }

    /**
     * 为指定的分类添加属性
     * @param categoryId
     * @param attribute
     * @return
     */
    public Attribute addAttribute(Long categoryId, Attribute attribute) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);
        attribute.setCategory(category);

        try {
            attributeRepository.save(attribute);
        }
        catch(DataIntegrityViolationException ex) {
            throw new IntegrityConstraintViolationException("一个分类不能创建名字重复的属性", ex);
        }

        return attribute;
    }


    /**
     * 为指定的属性添加选项值
     * @param attributeId
     * @param attributeOption
     * @return
     */
    public AttributeOption addAttributeOption(Long attributeId, AttributeOption attributeOption) {

        Attribute attribute = attributeRepository.findById(attributeId).orElseThrow(EntityNotFoundException::new);

        attributeOption.setAttribute(attribute);

        attributeOptionRepository.save(attributeOption);

        return attributeOption;
    }

    /**
     * 删除属性值
     * @param attributeOptionId
     * @return
     */
    public void deleteAttributeOption(Long attributeOptionId) {
        attributeOptionRepository.deleteById(attributeOptionId);
    }

    private boolean AttributesContains(List<Attribute> attributes, Attribute findAttribute) {
        for (Attribute attribute: attributes) {
            if (attribute.getName().equals(findAttribute.getName()))
                return true;
        }

        return false;
    }


}
