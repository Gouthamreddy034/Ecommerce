package com.yanala.ecommerce.services.admin.faq;

import com.yanala.ecommerce.dto.FAQDto;
import com.yanala.ecommerce.entity.FAQ;
import com.yanala.ecommerce.entity.Product;
import com.yanala.ecommerce.repository.FAQRepository;
import com.yanala.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService{

    private final FAQRepository faqRepository;

    private final ProductRepository productRepository;

    public FAQDto postFAQ(Long productId,FAQDto faqDto){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            FAQ faq = new FAQ();

            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());

            return faqRepository.save(faq).getFAQDto();
        }
        return null;
    }
}
