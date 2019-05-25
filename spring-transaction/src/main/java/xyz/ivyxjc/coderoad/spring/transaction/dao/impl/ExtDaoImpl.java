package xyz.ivyxjc.coderoad.spring.transaction.dao.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import xyz.ivyxjc.coderoad.spring.transaction.dao.ExtDao;
import xyz.ivyxjc.coderoad.spring.transaction.model.Ext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ExtDaoImpl implements ExtDao {
    private static final String QUERY_BY_GUID = "select * from EXT where GUID=:GUID";
    private static final String UPDATE_BY_GUID =
            "update EXT set EXT_AMOUNT=:EXT_AMOUNT, EXT_DATE=:EXT_DATE,UPDATED_AT=sysdate(),UPDATED_BY=user() where GUID=:GUID";
    private static final String INSERT_EXT =
            "insert into EXT (GUID, EXT_ID, EXT_AMOUNT, EXT_DATE, CREATED_AT, CREATED_BY) "
                    + "VALUE  (:GUID, :EXT_ID, :EXT_AMOUNT, :EXT_DATE, sysdate(), user())";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Nullable
    @Override
    public Ext queryByGuid(@NotNull String guid) {
        Map<String, String> map = new HashMap<>();
        map.put("guid", guid);
        jdbcTemplate.query(QUERY_BY_GUID, map, new RowMapper<Ext>() {
            @Override
            public Ext mapRow(ResultSet rs, int rowNum) throws SQLException {
                Ext ext = new Ext();
                ext.setGuid(rs.getString("GUID"));
                ext.setExtId(rs.getString("EXT_ID"));
                ext.setExtAmount(rs.getBigDecimal("EXT_AMOUNT"));
                ext.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                ext.setCreatedBy(rs.getString("CREATED_BY"));
                ext.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                ext.setUpdatedBy(rs.getString("UPDATED_BY"));
                return ext;
            }
        });
        return null;
    }

    @Transactional
    @Override
    public int insertExt(@Nullable Ext ext) {
        Map<String, Object> map = new HashMap<>();
        if (ext == null) {
            return -1;
        }
        map.put("GUID", ext.getGuid());
        map.put("EXT_ID", ext.getExtId());
        map.put("EXT_AMOUNT", ext.getExtAmount());
        map.put("EXT_DATE", ext.getExtDate());
        return jdbcTemplate.update(INSERT_EXT, map);
    }

    @Override
    public int updateExt(@NotNull Ext ext) {
        Map<String, Object> map = new HashMap<>();
        map.put("GUID", ext.getGuid());
        map.put("EXT_AMOUNT", ext.getExtAmount());
        map.put("EXT_DATE", ext.getExtDate());
        return jdbcTemplate.update(UPDATE_BY_GUID, map);
    }

}
