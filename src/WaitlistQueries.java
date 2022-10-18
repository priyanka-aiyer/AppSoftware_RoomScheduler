import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class WaitlistQueries {
    
    private static Connection connection;
    private static PreparedStatement psAddWaitlst;
    private static PreparedStatement psRoomWaitlisting;
    private static PreparedStatement psCheckFacultyWaitlist;
    private static PreparedStatement psRoomFacultyWaitlisting;
    private static PreparedStatement psCancelWaitlist;
    private static PreparedStatement psFacultyWaitlist;
    private static PreparedStatement psRoomWaitlistByDate;
    private static PreparedStatement psRoomWaitlistBySeats;
    private static ResultSet resultSet;

    //// constructor
    public WaitlistQueries() {   
        connection = DBConnection.getConnection();
    }
       
    ////////
    public static void addWaitlistEntry(String inputFaculty, String inputDate, String inputSeats)
    {
        //connection = DBConnection.getConnection();
        try
        {
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            
            psAddWaitlst = connection.prepareStatement("insert into WAITLIST (FACULTY, DATE, SEATS, TIMESTAMP) values (?, ?, ?, ?)");
            psAddWaitlst.setString(1, inputFaculty);
            psAddWaitlst.setString(2, inputDate);
            psAddWaitlst.setString(3, inputSeats);
            psAddWaitlst.setTimestamp(4, currentTimestamp);
            psAddWaitlst.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }        
    }

    public static void deleteWaitlistEntry(String inputFaculty, String inputDate)
    {      
        //connection = DBConnection.getConnection();       
        try
        {
            psCancelWaitlist = connection.prepareStatement("Delete from WAITLIST where FACULTY = ? and DATE = ? ");
            psCancelWaitlist.setString(1, inputFaculty);
            psCancelWaitlist.setString(2, inputDate);
            psCancelWaitlist.executeUpdate();            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

    //////// get() Methods
      
    public static List<WaitlistEntry> getWaitlistByDate(String inputDate)
    {      
        List<WaitlistEntry> resultsWaitlist = null;
        
        try
        {
            psRoomWaitlistByDate = connection.prepareStatement("select * from WAITLIST where DATE = ? order by DATE, TIMESTAMP");
            psRoomWaitlistByDate.setString(1, inputDate);
            resultSet = psRoomWaitlistByDate.executeQuery();
            
            resultsWaitlist = new ArrayList<WaitlistEntry>();
            
            while (resultSet.next() == true)
            {          
                resultsWaitlist.add(new WaitlistEntry( resultSet.getString("FACULTY"), resultSet.getDate("DATE"), resultSet.getInt("SEATS"), resultSet.getTimestamp("TIMESTAMP") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsWaitlist;
    }   

    public List<WaitlistEntry> getAllWaitLists()
    {      
        List<WaitlistEntry> resultsWaitlist = null;
        
        try
        {
            psRoomWaitlisting = connection.prepareStatement("select FACULTY, DATE, SEATS, TIMESTAMP from WAITLIST order by TIMESTAMP");
            resultSet = psRoomWaitlisting.executeQuery();
            
            resultsWaitlist = new ArrayList<WaitlistEntry>();
            
            while (resultSet.next() == true)
            {          
                resultsWaitlist.add(new WaitlistEntry( resultSet.getString("FACULTY"), resultSet.getDate("DATE"), resultSet.getInt("SEATS"), resultSet.getTimestamp("TIMESTAMP") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsWaitlist;
    }   

    public static List<WaitlistEntry> getWaitlistByFaculty(String inputFaculty)
    {      
        List<WaitlistEntry> resultsWaitlist = null;
        
        try
        {
            psRoomFacultyWaitlisting = connection.prepareStatement("select * from WAITLIST where FACULTY = ? order by DATE, TIMESTAMP");
            psRoomFacultyWaitlisting.setString(1, inputFaculty);
            resultSet = psRoomFacultyWaitlisting.executeQuery();
            
            resultsWaitlist = new ArrayList<WaitlistEntry>();
            
            while (resultSet.next() == true)
            {          
                resultsWaitlist.add(new WaitlistEntry( resultSet.getString("FACULTY"), resultSet.getDate("DATE"), resultSet.getInt("SEATS"), resultSet.getTimestamp("TIMESTAMP") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsWaitlist;
    }   

    public static List<WaitlistEntry> getAllFacultyWaitlist()
    {      
        List<WaitlistEntry> resultsWaitlist = null;
        
        try
        {
            psFacultyWaitlist = connection.prepareStatement("select * from WAITLIST order by DATE, TIMESTAMP");
            resultSet = psFacultyWaitlist.executeQuery();
            
            resultsWaitlist = new ArrayList<WaitlistEntry>();
            
            while (resultSet.next() == true)
            {          
                resultsWaitlist.add(new WaitlistEntry( resultSet.getString("FACULTY"), resultSet.getDate("DATE"), resultSet.getInt("SEATS"), resultSet.getTimestamp("TIMESTAMP") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsWaitlist;
    }   

    public static List<WaitlistEntry> getWaitlistBySeats(int inputSeats)
    {      
        List<WaitlistEntry> resultsWaitlistBySeats = null;
        
        try
        {
            psRoomWaitlistBySeats = connection.prepareStatement("select * from WAITLIST where SEATS <= ? order by DATE, TIMESTAMP");
            psRoomWaitlistBySeats.setInt(1, inputSeats);
            resultSet = psRoomWaitlistBySeats.executeQuery();
            
            resultsWaitlistBySeats = new ArrayList<WaitlistEntry>();
            
            while (resultSet.next() == true)
            {          
                resultsWaitlistBySeats.add(new WaitlistEntry( resultSet.getString("FACULTY"), resultSet.getDate("DATE"), resultSet.getInt("SEATS"), resultSet.getTimestamp("TIMESTAMP") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsWaitlistBySeats;
    }   

    //// check() Methods
    
    public static Boolean checkWaitlistByFaculty(String inputFaculty, String inputDate)
    {      
        //connection = DBConnection.getConnection();       
        try
        {
            psCheckFacultyWaitlist = connection.prepareStatement("select FACULTY from WAITLIST where FACULTY = ? and DATE = ? ");
            psCheckFacultyWaitlist.setString(1, inputFaculty);
            psCheckFacultyWaitlist.setString(2, inputDate);
            resultSet = psCheckFacultyWaitlist.executeQuery();
            
            // If true, then Faculty already has a room Waitlisted on this date
            if (resultSet.next() == true)
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
