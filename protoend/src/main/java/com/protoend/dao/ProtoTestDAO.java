package com.protoend.dao;


import com.protoend.base.model.enumerator.AuthType;
import com.protoend.base.model.enumerator.ConnectionType;
import com.protoend.base.model.enumerator.TestStatus;
import com.protoend.model.ProtoEnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProtoTestDAO  {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String schema ="connection";

    private Connection connection;


    public List<ProtoEnd> getAll() throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet resultSet = statement.executeQuery("select * from connection");
        List<ProtoEnd> protoEnds = new ArrayList<>();

        while (resultSet.next()){
            ProtoEnd protoEnd = new ProtoEnd();
            protoEnd.setId(resultSet.getInt(1));
            protoEnd.setAuth(resultSet.getBytes(2));
            protoEnd.setAuthType(AuthType.values()[resultSet.getInt(3)]);
            protoEnd.setConnectionType(ConnectionType.values()[resultSet.getInt(4)]);
            protoEnd.setRequestDetail(resultSet.getBytes(5));
            protoEnd.setStatus(TestStatus.values()[resultSet.getInt(6)]);
            protoEnd.setUrl(resultSet.getString(7));
            protoEnds.add(protoEnd);
        }
        connection.close();
        return protoEnds;
    }

}
