package com.yanala.ecommerce.services.admin.faq;

import com.yanala.ecommerce.dto.FAQDto;

public interface FAQService {
    FAQDto postFAQ(Long productId, FAQDto faqDto);
}
