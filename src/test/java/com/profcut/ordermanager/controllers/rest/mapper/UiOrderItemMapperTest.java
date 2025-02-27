package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UiOrderItemMapperTest {

    @Mock
    UiProductMapper uiProductMapper;
    @Mock
    UiMaterialMapper uiMaterialMapper;

    @InjectMocks
    UiOrderItemMapperImpl orderItemMapper;

    @Test
    void shouldMapAllFields() {
        var orderItem = TestDataHelper.getDefaultOrderItem();

        var result = orderItemMapper.apply(orderItem);

        assertNotNull(result);
        assertThat(orderItem).usingRecursiveComparison()
                .ignoringFields("order", "technologist", "material", "product", "machines")
                .isEqualTo(result);
    }
}
