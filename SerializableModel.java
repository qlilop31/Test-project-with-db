package com.qlilop.kurs;

import java.sql.ResultSet;

public interface SerializableModel {
    String getQuery();
    void serialize(ResultSet resultSet);
}
