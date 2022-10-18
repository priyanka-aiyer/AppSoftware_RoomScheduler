import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class DatesQueries {
    
    private static Connection connection;   
    private static PreparedStatement psAddDate;
    private static PreparedStatement psGetDate;
    private static PreparedStatement psGetAllDates;
    private static ResultSet resultSet;
    
        
    //// constructor
    public DatesQueries() {   
        connection = DBConnection.getConnection();
    }
       
    //////////////////////////////////////////
    
    public static void addDate(Date spinnerDate)
    {
        try
        {
            psAddDate = connection.prepareStatement("insert into DATES (DATE) values (?)");
            psAddDate.setDate(1, spinnerDate);
            psAddDate.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }        
    }
    
    //// get() Methods
    
    public List<Dates> getAllDates()
    {
        List<Dates> resultsDatesList = null;
        
        try
        {
            psGetAllDates = connection.prepareStatement("select DATE from DATES order by DATE");
            resultSet = psGetAllDates.executeQuery();

            resultsDatesList = new ArrayList<Dates>();
            
            while(resultSet.next())
            {
                resultsDatesList.add(new Dates( resultSet.getDate("DATE") ));         
                
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return resultsDatesList;
        
    }

    //// check() Methods
    
    public static Boolean checkDate(Date spinnerDate)
    {
        try
        {
            psGetDate = connection.prepareStatement("select DATE from DATES where DATE = ? order by DATE ");
            psGetDate.setDate(1, spinnerDate);
            resultSet = psGetDate.executeQuery();
            
            while(resultSet.next())
            {
                return true;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return false;
    }
    
}
