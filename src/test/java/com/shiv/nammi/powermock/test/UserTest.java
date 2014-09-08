package com.shiv.nammi.powermock.test;

import com.shiv.nammi.powermock.common.DBConnection;
import com.shiv.nammi.powermock.database.UserDAO;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;

import static org.easymock.EasyMock.replay;
import static org.powermock.api.easymock.PowerMock.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sn1 on 9/8/14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({UserDAO.class, DBConnection.class})
public class UserTest {

    String sql = "Select * from users";

    @Before
    public void init(){

    }

    @Test
    public void mockGetUsers() throws Exception {
        createMocksAndSetExpectations();

        UserDAO userDAO = new UserDAO();

        String userName = userDAO.getUserName();

        System.out.println("***************" + userName);

        Assert.assertEquals(userName, "Nammi");
    //    Assert.assertEquals(userName, "Nammii");

    }

    private void createMocksAndSetExpectations() throws SQLException {
        Connection mockConnection = createMock(Connection.class);
        PreparedStatement mockStatement = createMock(PreparedStatement.class);
        ResultSet resultSet = createMock(ResultSet.class);
        DBConnection dbConnection = createMock(DBConnection.class);

        mockStatic(DBConnection.class);
        expect(DBConnection.getInstance()).andReturn(dbConnection);

        PowerMock.replay(DBConnection.class);

        expect(dbConnection.openConnection()).andReturn(mockConnection);
        replay(dbConnection);

        expect(mockConnection.prepareStatement(sql)).andReturn(mockStatement).anyTimes();
        replay(mockConnection);

        expect(mockStatement.executeQuery()).andReturn(resultSet);
        replay(mockStatement);

        expect(resultSet.next()).andReturn(true);
        expect(resultSet.getString("name")).andReturn("Nammi");
        PowerMock.replay(resultSet);

    }


}
