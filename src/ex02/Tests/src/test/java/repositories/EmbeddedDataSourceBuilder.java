package repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import javax.sql.DataSource;
import static org.junit.jupiter.api.Assertions.*;

public class EmbeddedDataSourceBuilder {
    public DataSource dataSource;

    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder().generateUniqueName(true).addDefaultScripts().build();
    }

    @Test
    public void getConnection() {
        assertNotNull(dataSource);
    }

}
