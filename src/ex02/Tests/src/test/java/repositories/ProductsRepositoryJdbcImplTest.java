package repositories;

import edu.school21.models.Product;
import edu.school21.repositories.ProductsRepositoryJdbcImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {
    ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(null);
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(new Product(0L, "Beer", 55.05),
            new Product(1L, "Lion meet", 100.90),
            new Product(2L, "Chicken", 10.2),
            new Product(3L, "Fish", 13.3),
            new Product(4L, "Monster", 1000.234));
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(0L, "Beer", 55.05);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(0L, "Pain", 1.666);
    final Product EXPECTED_SAVE_PRODUCT = new Product(5L, "Alibaba", 1000.123);
    @BeforeEach
    public void init() {
        EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder().generateUniqueName(true).addDefaultScripts().build();
        productsRepositoryJdbc.setDataSource(dataSource);
    }

    @Test
    public void findAll() {
        List<Product> productList = productsRepositoryJdbc.findAll();
        assertArrayEquals(productList.toArray(), EXPECTED_FIND_ALL_PRODUCTS.toArray());
    }

    @Test
    public void findById() {
        Optional<Product> optionalProduct = productsRepositoryJdbc.findById(0L);
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            assertEquals(product, EXPECTED_FIND_BY_ID_PRODUCT);
        }
    }

    @Test
    public void update() {
        productsRepositoryJdbc.update(EXPECTED_UPDATED_PRODUCT);
        Optional<Product> optionalProduct = productsRepositoryJdbc.findById(0L);
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            assertEquals(product, EXPECTED_UPDATED_PRODUCT);
        }
    }

    @Test
    public void delete() {
        productsRepositoryJdbc.delete(0L);
        Optional<Product> optionalProduct = productsRepositoryJdbc.findById(0L);
        assertNull(optionalProduct.orElse(null));
    }

    @Test
    public void save() {
        productsRepositoryJdbc.save(EXPECTED_SAVE_PRODUCT);
        Optional<Product> optionalProduct = productsRepositoryJdbc.findById(5L);
        optionalProduct.ifPresent(product -> assertEquals(EXPECTED_SAVE_PRODUCT, product));
    }

    @AfterEach
    public void tearDown() {
        productsRepositoryJdbc.getDataSource().shutdown();
    }
}
