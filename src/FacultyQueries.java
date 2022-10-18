
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Faculty Class for QUERIES on Faculty entity
public class FacultyQueries
{
    private static Connection connection;
    private static PreparedStatement psAddFaculty;
    private static PreparedStatement psGetFacultyList;
    private static PreparedStatement psGetFacultyName;
    private static ResultSet resultSet;
    private static ArrayList<String> faculty = new ArrayList<String>();
    

    //// constructor
    public FacultyQueries() {   
        connection = DBConnection.getConnection();
    }
    
    ////////    
    public static void addFaculty(String name)
    {
        
        try
        {
            psAddFaculty = connection.prepareStatement("insert into faculty (name) values (?)");
            psAddFaculty.setString(1, name);
            psAddFaculty.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    //// get() Methods
    
    public List<Faculty> getAllFaculty()
    {
        
        List<Faculty> resultsFacultyList = null;
        
        try
        {
            psGetFacultyList = connection.prepareStatement("select NAME from FACULTY order by NAME");
            resultSet = psGetFacultyList.executeQuery();

            resultsFacultyList = new ArrayList<Faculty>();
            
            while(resultSet.next())
            {
                resultsFacultyList.add(new Faculty( resultSet.getString("NAME") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return resultsFacultyList;
        
    }

    //// check() Methods
    
    public static Boolean checkFacultyByName(String facultyName)
    {
        
        try
        {
            psGetFacultyName = connection.prepareStatement("select NAME from FACULTY where NAME = ? order by NAME ");
            psGetFacultyName.setString(1, facultyName);
            resultSet = psGetFacultyName.executeQuery();
            
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
