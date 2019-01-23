package com.parves1527.web.dao;

import com.parves1527.web.model.FileMeta;
import java.util.List;

public interface FileMetaDao
{
    boolean add(FileMeta fileData);
    
    boolean update(String status, int id);

    List<FileMeta> get(int start, int end);
    
    FileMeta get(int id);        
}
