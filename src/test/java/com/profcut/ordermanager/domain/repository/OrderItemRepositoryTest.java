package com.profcut.ordermanager.domain.repository;

import com.profcut.ordermanager.ConfigurationTestBeans;
import com.profcut.ordermanager.domain.entities.MaterialEntity;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.entities.ProductEntity;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(value = {ConfigurationTestBeans.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderItemRepositoryTest {

    @Autowired
    OrderItemRepository repository;

    @Test
    void repoTest() {
        var order = TestDataHelper.buildDefaultOrder();
        var item = new OrderItemEntity()
                .setOrder(order)
                .setQuantity(0)
                .setQuantityShipped(0)
                .setProgramWritten(false)
                .setMaterial(new MaterialEntity().setMaterialType("Сталь"))
                .setProduct(new ProductEntity().setProductName("Радиатор"));
        var saved = repository.save(item);
        System.out.println(saved);
    }
}