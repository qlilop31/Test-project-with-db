package com.qlilop.kurs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface SerializableModelWithUserInput  {

    PreparedStatement getPreparedStatement(Connection connection);
    void serialize(ResultSet resultSet);

}
