package xyz.ivyxjc.coderoad.spring.transaction.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import xyz.ivyxjc.coderoad.spring.transaction.dao.ExtDao;
import xyz.ivyxjc.coderoad.spring.transaction.dao.TransDao;
import xyz.ivyxjc.coderoad.spring.transaction.model.Ext;
import xyz.ivyxjc.coderoad.spring.transaction.model.Trans;
import xyz.ivyxjc.coderoad.spring.transaction.service.TransService;

@Slf4j
@Service
public class TransServiceImpl implements TransService {

    @Autowired
    private TransDao transDao;

    @Autowired
    private ExtDao extDao;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Transactional
    @Override
    public int insert(Trans trans, Ext ext) {
        int transRes = transDao.insertTrans(trans);
        Thread thread = new Thread() {
            @Override
            public void run() {
                int extRes = extDao.insertExt(ext);
            }
        };
        thread.start();
        try {
            Thread.sleep(2000);
            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
        } catch (InterruptedException e) {
            log.error("thread sleep throw exceptions", e);
        }
        return transRes == 1 ? 1 : -1;
    }

    @Override
    public int update(Trans trans, Ext ext) {
        int transRes = transDao.updateTrans(trans);
        int extRes = extDao.updateExt(ext);
        return transRes == extRes && transRes == 1 ? 1 : -1;
    }
}
