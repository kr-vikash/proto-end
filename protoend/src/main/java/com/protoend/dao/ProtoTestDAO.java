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


    public List<ProtoEnd> getAll(String connectionType) throws SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        Statement statement = connection.createStatement();
        ConnectionType conn = ConnectionType.valueOf(connectionType.toUpperCase());
        statement.setQueryTimeout(30);
        ResultSet resultSet = statement.executeQuery("select * from connection where type=\'"+ conn.ordinal() + "\' order by created_time DESC;");
        List<ProtoEnd> protoEnds = new ArrayList<>();

        while (resultSet.next()){
            ProtoEnd protoEnd = new ProtoEnd();
            protoEnd.setId(resultSet.getInt("id"));
            protoEnd.setAuth(resultSet.getBytes("auth"));
            protoEnd.setAuthType(AuthType.values()[resultSet.getInt("auth_type")]);
            protoEnd.setConnectionType(ConnectionType.values()[resultSet.getInt("type")]);
            protoEnd.setCreatedTime(resultSet.getLong("created_time"));
            protoEnd.setRequestDetail(resultSet.getBytes("request_detail"));
            protoEnd.setStatus(TestStatus.values()[resultSet.getInt("status")]);
            protoEnd.setUrl(resultSet.getString("url"));
            protoEnd.setAdditionalProperties(resultSet.getBytes("additional_properties"));
            protoEnds.add(protoEnd);
        }
        connection.close();
        return protoEnds;
    }

}
