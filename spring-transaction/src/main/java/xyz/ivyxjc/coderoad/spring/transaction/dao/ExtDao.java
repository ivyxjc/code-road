package xyz.ivyxjc.coderoad.spring.transaction.dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import xyz.ivyxjc.coderoad.spring.transaction.model.Ext;

public interface ExtDao {

    @Nullable
    Ext queryByGuid(@NotNull String guid);

    int insertExt(@Nullable Ext ext);

    int updateExt(@NotNull Ext ext);
}
