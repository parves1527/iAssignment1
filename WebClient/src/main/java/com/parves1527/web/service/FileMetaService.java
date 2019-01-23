package com.parves1527.web.service;

import com.parves1527.web.model.FileMeta;
import java.util.List;

public interface FileMetaService
{
    boolean add(FileMeta fileMeta);

    boolean update(String status, int id);
    
    List<FileMeta> get(int start, int end);
    
    FileMeta get(int id);
}
