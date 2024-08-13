package com.yanala.ecommerce.services.admin.adminproduct;

import com.yanala.ecommerce.dto.ProductDto;
import com.yanala.ecommerce.entity.Category;
import com.yanala.ecommerce.entity.Product;
import com.yanala.ecommerce.repository.CategoryRepository;
import com.yanala.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public ProductDto addProduct(ProductDto productDto) throws IOException {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImg(productDto.getImg().getBytes());

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();

        product.setCategory(category);
        return productRepository.save(product).getDto();
    }

    @Override
    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductsByName(String title){
        List<Product> products = productRepository.findAllByNameContaining(title);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public boolean deleteProduct(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductDto getProductById(Long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(Product::getDto).orElse(null);
    }

    public ProductDto updateProduct(Long productId,ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(optionalProduct.isPresent()&&optionalCategory.isPresent()){
            Product product = optionalProduct.get();

            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCategory(optionalCategory.get());
            if(productDto.getImg()!=null){
                product.setImg(productDto.getImg().getBytes());
            }
            return productRepository.save(product).getDto();
        }else {
            return null;
        }
    }
}
