package com.beijiake.web.rest;


import com.beijiake.data.domain.product.Product;
import com.beijiake.data.domain.product.ProductImage;
import com.beijiake.data.domain.resouce.File;
import com.beijiake.data.domain.resouce.FileUsage;
import com.beijiake.data.domain.product.ProductAttribute;
import com.beijiake.repository.product.ProductAttributeRepository;
import com.beijiake.repository.product.ProductImageRepository;
import com.beijiake.repository.product.ProductRepository;
import com.beijiake.repository.resource.FileRepository;
import com.beijiake.repository.resource.FileUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/${url.api.prefix:/api}/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileUsageRepository fileUsageRepository;

    @Autowired
    ProductAttributeRepository productAttributeRepository;


    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public void createProduct(@RequestBody Product product) {

        productRepository.save(product);
    }

    @GetMapping()
    public PageResponse<Product> getProducts(Pageable pageRequest) {
        return PageResponse.of(productRepository.findAll(pageRequest));
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(String.format("product with id %s does not exist", id)));
        return product;

    }


    @PostMapping("/{id}/images")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addNewImageToProduct(@PathVariable(name = "id") Long productId, @RequestBody List<String> images) {


        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException(String.format("product with id %s does not exist", productId)));


        /**
         * 为产品添加图片必须预先将图片上传的服务器，然后将图片登记到资源文件使用者记录中，否则服务器后台进程会定期检查上传的文件是否有使用
         * 者，如果没有的话，那么距离上传时间超过8小时（具体由服务器配置决定）但无任何使用者的文件会从服务器删除。
         */
        images.forEach((image)->{

            Optional<File> f = fileRepository.findByFilename(image);

            if (!f.isPresent())
                return;

            /* 如果没有登记，那么登记*/
            if (!fileUsageRepository.findFirstByFileAndOwnerTypeAndOwnerId(f.get(), "production", productId.toString()).isPresent()) {

                FileUsage fileUsage = new FileUsage();

                fileUsage.setFile(f.get());
                fileUsage.setOwnerType("production");
                fileUsage.setOwnerId(productId.toString());

                fileUsageRepository.save(fileUsage);
            }

            String url = f.get().getUrl();

            if (!url.endsWith("/"))
                url += "/";

            String resourceUrl = url+f.get().getFilename();

            Optional<ProductImage> pi = productImageRepository.findFirstByProductAndResourceUrl(product, resourceUrl);

            if (pi.isPresent())
                return;


            ProductImage productImage = new ProductImage();

            productImage.setProduct(product);
            productImage.setResourceUrl(resourceUrl);

            productImageRepository.save(productImage);

        });


    }

    @GetMapping("/{productId}/images")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ProductImage> getImagesOfProduct(@PathVariable Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException(String.format("product with id %s does not exist", productId)));

        return productImageRepository.findAllByProduct(product);


    }


    /**
     * Add Attributes to product
     */
    @PostMapping("/{productId}/attributes")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void doPostAttributes(@PathVariable Long productId, @RequestBody List<ProductAttribute> productAttributes) {

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException(String.format("product with id %s does not exist", productId)));


        for(ProductAttribute productAttribute: productAttributes) {
            productAttribute.setProduct(product);
            productAttributeRepository.save(productAttribute);
        }

    }


}
