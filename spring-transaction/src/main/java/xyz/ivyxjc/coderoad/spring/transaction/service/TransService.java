package xyz.ivyxjc.coderoad.spring.transaction.service;

import xyz.ivyxjc.coderoad.spring.transaction.model.Ext;
import xyz.ivyxjc.coderoad.spring.transaction.model.Trans;

public interface TransService {

    int insert(Trans trans, Ext ext);

    int update(Trans trans, Ext ext);
}
