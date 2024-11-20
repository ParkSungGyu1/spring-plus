package org.example.expert.testuser.repository;

import lombok.RequiredArgsConstructor;
import org.example.expert.testuser.entity.TestUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserTestRepository {


    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void bulkInsertUsers(List<TestUser> users) {
        String sql = "INSERT INTO test_user (id, name, age) VALUES (?, ?, ?)";
        
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             
            int batchSize = 1000; // 배치 크기 설정
            int count = 0;

            for (TestUser user : users) {
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.addBatch(); // 배치에 추가

                if (++count % batchSize == 0) {
                    preparedStatement.executeBatch(); // 배치 실행
                }
            }
            preparedStatement.executeBatch(); // 남은 배치 실행
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 처리
        }
    }

    public TestUser findByName(int name) {
        TestUser user = null;
        String sql = "SELECT id, name, age FROM test_user WHERE age = ?";

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new TestUser();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외 처리
        }

        return user;
    }
}