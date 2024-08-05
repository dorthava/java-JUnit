package edu.school21.repositories;

import edu.school21.models.Product;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private EmbeddedDatabase dataSource;

    public ProductsRepositoryJdbcImpl(EmbeddedDatabase dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        String statement = "SELECT * FROM product";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Long identifier = resultSet.getLong("identifier");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Product product = new Product(identifier, name, price);
                productList.add(product);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String statement = "SELECT * FROM product WHERE identifier = ?";
        Product result = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Long identifier = resultSet.getLong("identifier");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                result = new Product(identifier, name, price);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void update(Product product) {
        if(product == null) return;
        String statement = "UPDATE product SET name = ?, price = ? WHERE identifier = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(statement)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setDouble(2, product.getPrice());
            prepareStatement.setLong(3, product.getIdentifier());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void save(Product product) {
        if(product == null) return;
        String statement = "INSERT INTO product (name, price) VALUES (?, ?);";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(statement)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setDouble(2, product.getPrice());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        if(id == null) return;
        String statement = "DELETE FROM product WHERE identifier = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(statement)) {
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public EmbeddedDatabase getDataSource() {
        return dataSource;
    }

    public void setDataSource(EmbeddedDatabase dataSource) {
        this.dataSource = dataSource;
    }
}
