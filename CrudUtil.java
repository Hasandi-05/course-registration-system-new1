/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ijse.layered.dao;

import edu.ijse.layered.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author anjan
 */
public class CrudUtil {
    private static PreparedStatement getPreparedStatement(String sql, Object... args) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            statement.setObject(i + 1, args[i]);
        }
        return statement;
    }

    public static boolean executeUpdate(String sql, Object... args) throws Exception {
        PreparedStatement preparedStatement = getPreparedStatement(sql, args);
        return preparedStatement.executeUpdate() > 0;
    }

    public static ResultSet executeQuery(String sql, Object... args) throws Exception {
        PreparedStatement preparedStatement = getPreparedStatement(sql, args);
        return preparedStatement.executeQuery();
    }
}
