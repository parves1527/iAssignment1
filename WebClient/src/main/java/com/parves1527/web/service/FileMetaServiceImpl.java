package com.parves1527.web.service;

import com.parves1527.web.dao.FileMetaDao;
import com.parves1527.web.model.FileMeta;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class FileMetaServiceImpl implements FileMetaService
{
    @Autowired
    private FileMetaDao fileMetaDao;
    
    @Override
    public boolean add(FileMeta user)
    {
        return fileMetaDao.add(user);
    }
    
    @Override
    public boolean update(String status, int id)
    {
        return fileMetaDao.update(status, id);
    }

    @Override
    public List<FileMeta> get(int start, int end)
    {
        return fileMetaDao.get(start, end);
    }    
    
    @Override
    public FileMeta get(int id)
    {
        return fileMetaDao.get(id);
    }    
}
