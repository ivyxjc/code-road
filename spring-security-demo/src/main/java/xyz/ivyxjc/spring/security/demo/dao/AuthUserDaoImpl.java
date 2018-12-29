package xyz.ivyxjc.spring.security.demo.dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivyxjc
 * @since 9/24/2018
 */
@Repository
public class AuthUserDaoImpl implements AuthUserDao {
    private static final String GET_USER =
            "SELECT USER_ID,USER_NAME,PASSWORD FROM USER WHERE USER_NAME=:USER_NAME";

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Nullable
    public AuthUser getUser(@NotNull String username) {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("USER_NAME", username);
        return namedJdbcTemplate.queryForObject(GET_USER, parameterMap, new CUserMapper());
    }

    class CUserMapper implements RowMapper<AuthUser> {
        @Override
        public AuthUser mapRow(ResultSet resultSet, int i) throws SQLException {
            AuthUser authUser = new AuthUser();
            authUser.setUserId(resultSet.getLong("USER_ID"));
            authUser.setUsername(resultSet.getString("USER_NAME"));
            authUser.setPassword(resultSet.getString("PASSWORD"));
            return authUser;
        }
    }
}
