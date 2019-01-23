package com.parves1527.web.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.parves1527.web.model.FileMeta;

public class FileMetaDaoImpl implements FileMetaDao
{
    @Autowired
    DataSource datasource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean add(FileMeta fileMeta)
    {
        String sql = "insert into file_meta"
                + "(uri, file_name, size, file_type, download_path, status)"
                + " values(?,?,?,?,?,?)";

        jdbcTemplate.update(sql, new Object[]
        {
            fileMeta.getUri(), fileMeta.getFileName(),
            fileMeta.getSize(), fileMeta.getFileType(),
            fileMeta.getDownloadPath(), fileMeta.getStatus()
        });
        
        return true;
    }
    
    @Override
    public boolean update(String status, int id)
    {
        String sql = "update file_meta set status=? where id=?";

        jdbcTemplate.update(sql, new Object[]
        {
            status, id
        });
        
        System.out.println(sql);
        
        return true;
    }

    @Override
    public List<FileMeta> get(int start, int end)
    {
        if(start > end)
        {
            int temp = end;
            end = start;
            start = temp;
        }
        String sql = "select * from file_meta LIMIT " + start + "," + end;

        List<FileMeta> fileMetas = jdbcTemplate.query(sql, new FileMetaMapper());

        return fileMetas;
    }
    
    @Override
    public FileMeta get(int id)
    {
        String sql = "select * from file_meta where id='" + id + "'";

        List<FileMeta> fileMetas = jdbcTemplate.query(sql, new FileMetaMapper());

        return fileMetas.isEmpty() ? null : fileMetas.get(0);
    }

}

class FileMetaMapper implements RowMapper<FileMeta>
{
    @Override
    public FileMeta mapRow(ResultSet rs, int arg1) throws SQLException
    {
        FileMeta fileMeta = new FileMeta();
        fileMeta.setId(rs.getInt("id"));
        fileMeta.setUri(rs.getString("uri"));
        fileMeta.setFileName(rs.getString("file_name"));
        fileMeta.setSize(rs.getLong("size"));
        fileMeta.setFileType(rs.getString("file_type"));
        fileMeta.setDownloadPath(rs.getString("download_path"));
        fileMeta.setStatus(rs.getString("status"));

        return fileMeta;
    }
}
