package xyz.ivyxjc.coderoad.spring.transaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.ivyxjc.coderoad.spring.transaction.dao.ExtDao;
import xyz.ivyxjc.coderoad.spring.transaction.dao.TransDao;
import xyz.ivyxjc.coderoad.spring.transaction.model.Ext;
import xyz.ivyxjc.coderoad.spring.transaction.model.Trans;
import xyz.ivyxjc.coderoad.spring.transaction.service.TransService;

@Service
public class TransServiceImpl implements TransService {

    @Autowired
    private TransDao transDao;

    @Autowired
    private ExtDao extDao;

    @Transactional
    @Override
    public int insert(Trans trans, Ext ext) {
        int transRes = transDao.insertTrans(trans);
        int extRes = extDao.insertExt(ext);
        return transRes == extRes && transRes == 1 ? 1 : -1;
    }

    @Override
    public int update(Trans trans, Ext ext) {
        int transRes = transDao.updateTrans(trans);
        int extRes = extDao.updateExt(ext);
        return transRes == extRes && transRes == 1 ? 1 : -1;
    }
}
