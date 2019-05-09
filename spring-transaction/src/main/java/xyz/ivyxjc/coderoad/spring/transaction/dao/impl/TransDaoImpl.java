package xyz.ivyxjc.coderoad.spring.transaction.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import xyz.ivyxjc.coderoad.spring.transaction.dao.TransDao;
import xyz.ivyxjc.coderoad.spring.transaction.model.Trans;

@Repository
public class TransDaoImpl implements TransDao {
    private static final String QUERY_BY_GUID = "select * from TRANS where GUID=:GUID";
    private static final String UPDATE_BY_GUID =
        "update TRANS set TRANS_AMOUNT=:TRANS_AMOUNT, TRANS_DATE=:TRANS_DATE,UPDATED_AT=sysdate(),UPDATED_BY=user() where GUID=:GUID";
    private static final String INSERT_TRANS =
        "insert into TRANS (GUID, TRANS_ID, TRANS_AMOUNT, TRANS_DATE, CREATED_AT, CREATED_BY) "
            + "VALUE  (:GUID, :TRANS_ID, :TRANS_AMOUNT, :TRANS_DATE, sysdate(), user())";
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Nullable
    @Override
    public Trans queryByGuid() {
        return null;
    }

    @Override
    public int insertTrans(@Nullable Trans trans) {
        Map<String, Object> map = new HashMap<>();
        map.put("GUID", trans.getGuid());
        map.put("TRANS_ID", trans.getTransId());
        map.put("TRANS_AMOUNT", trans.getTransAmount());
        map.put("TRANS_DATE", trans.getTransDate());
        return jdbcTemplate.update(INSERT_TRANS, map);
    }

    @Override
    public int updateTrans(@NotNull Trans trans) {
        return 0;
    }
}
