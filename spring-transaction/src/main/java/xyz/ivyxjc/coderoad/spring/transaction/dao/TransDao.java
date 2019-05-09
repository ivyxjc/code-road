package xyz.ivyxjc.coderoad.spring.transaction.dao;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import xyz.ivyxjc.coderoad.spring.transaction.model.Trans;

public interface TransDao {

    @Nullable
    Trans queryByGuid();

    int insertTrans(@Nullable Trans trans);

    int updateTrans(@NotNull Trans transL);
}
