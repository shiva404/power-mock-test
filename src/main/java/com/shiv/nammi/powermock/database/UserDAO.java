package com.shiv.nammi.powermock.database;

import com.shiv.nammi.powermock.common.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sn1 on 9/6/14.
 */
public class UserDAO {


    private void populateUsers(int startId, int noOfusers) throws Exception {
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into users(user_id, migration_status) values(?,?)");
        for (int i = 0; i < noOfusers; i++){
            preparedStatement.setInt(1, startId + i);
            preparedStatement.setInt(2, 0);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }


    private ResultSet getData() throws SQLException {
        DBConnection dbConnection = DBConnection.getInstance();
        Connection connection = dbConnection.openConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Select * from users");
        return preparedStatement.executeQuery();
    }

    public String getUserName() throws Exception {
        ResultSet resultSet = getData();

        while(resultSet.next()){
            String name = resultSet.getString("name");
            if(name.equals("Nammi")){
                return "Nammi";
            }
        }
        return "Shiv";
    }
}
