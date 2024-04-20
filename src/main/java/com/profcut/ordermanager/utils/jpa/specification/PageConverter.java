package com.profcut.ordermanager.utils.jpa.specification;

import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@UtilityClass
public class PageConverter {

    private static final PageRequest DEFAULT_PAGE_REQUEST = new PageRequest(0, 20);

    public static Pageable covertToPageable(PageRequest pageRequest) {
        return Optional.ofNullable(pageRequest)
                .map(req -> org.springframework.data.domain.PageRequest.of(req.getPage(), req.getSize()))
                .orElse(org.springframework.data.domain.PageRequest.of(DEFAULT_PAGE_REQUEST.getPage(), DEFAULT_PAGE_REQUEST.getSize()));
    }
}
