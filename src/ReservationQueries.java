import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class ReservationQueries {
    private static Connection connection;
    private static PreparedStatement psAddReservation;
    private static PreparedStatement psCheckFacultyReservation;
    private static PreparedStatement psCheckRoomReservation;
    private static PreparedStatement psRoomReservations;
    private static PreparedStatement psRoomReservationsByFaculty;
    private static PreparedStatement psCancelFacultyReservation;
    private static PreparedStatement psFacultyReservations;
    private static PreparedStatement psReservationsByRoom;
    private static ResultSet resultSet;
    

    //// constructor
    public ReservationQueries() {   
        connection = DBConnection.getConnection();
    }
    
    ////////
    public static void addReservationEntry(String inputFaculty, String inputDate, String inputSeats, String roomName)
    {
        //connection = DBConnection.getConnection();
        try
        {
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());           
            psAddReservation = connection.prepareStatement("insert into RESERVATIONS (FACULTY, DATE, SEATS, ROOM, TIMESTAMP) values (?, ?, ?, ?, ?)");
            psAddReservation.setString(1, inputFaculty);
            psAddReservation.setString(2, inputDate);
            psAddReservation.setString(3, inputSeats);
            psAddReservation.setString(4, roomName);
            psAddReservation.setTimestamp(5, currentTimestamp);
            psAddReservation.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static void deleteReservation(String inputFaculty, String inputDate)
    {      
        //connection = DBConnection.getConnection();
        try
        {
            psCancelFacultyReservation = connection.prepareStatement("Delete from RESERVATIONS where FACULTY = ? and DATE = ? ");
            psCancelFacultyReservation.setString(1, inputFaculty);
            psCancelFacultyReservation.setString(2, inputDate);
            psCancelFacultyReservation.executeUpdate();            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

    }

    //////// get() Methods    
    
    public static List<ReservationEntry> getReservationsByFacultyForDate(String inputFaculty, String inputDate)
    {      
        //connection = DBConnection.getConnection();
        List<ReservationEntry> resultsReservation = null;
        
        try
        {
            psCheckFacultyReservation = connection.prepareStatement("select * from RESERVATIONS where FACULTY = ? and DATE = ? ");
            psCheckFacultyReservation.setString(1, inputFaculty);
            psCheckFacultyReservation.setString(2, inputDate);
            resultSet = psCheckFacultyReservation.executeQuery();

            resultsReservation = new ArrayList<ReservationEntry>();

            // If true, then Faculty already has a room Reserved on this date
            if (resultSet.next() == true)
            {               
                resultsReservation.add(new ReservationEntry( resultSet.getString("FACULTY"), resultSet.getString("ROOM"), resultSet.getDate("DATE"), resultSet.getInt("SEATS"), resultSet.getTimestamp("TIMESTAMP") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsReservation;
    }

    public static List<ReservationEntry> getRoomsReservedByDate(String inputDate)
    {      
        List<ReservationEntry> resultsReservationList = null;

        try
        {
            psCheckRoomReservation = connection.prepareStatement("select * from RESERVATIONS where DATE = ?");
            psCheckRoomReservation.setString(1, inputDate);
            resultSet = psCheckRoomReservation.executeQuery();
            
            resultsReservationList = new ArrayList<ReservationEntry>();

            while (resultSet.next() == true)
            {
                resultsReservationList.add(new ReservationEntry( resultSet.getString("FACULTY"), resultSet.getString("ROOM"), resultSet.getDate("DATE"), resultSet.getInt("SEATS"), resultSet.getTimestamp("TIMESTAMP") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsReservationList;
    }

////

    public static List<ReservationEntry> getReservationsByDate(String inputDate)
    {      
        List<ReservationEntry> resultsReservationList = null;
 
        try
        {
            psRoomReservations = connection.prepareStatement("select * from RESERVATIONS where DATE = ? order by DATE, TIMESTAMP");
            psRoomReservations.setString(1, inputDate);
            resultSet = psRoomReservations.executeQuery();
            
            resultsReservationList = new ArrayList<ReservationEntry>();
            
            while (resultSet.next() == true)
            {
                resultsReservationList.add(new ReservationEntry( resultSet.getString("FACULTY"), resultSet.getString("ROOM"), resultSet.getDate("DATE"), resultSet.getInt("SEATS"), resultSet.getTimestamp("TIMESTAMP") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsReservationList;
    }   

    public static List<ReservationEntry> getReservationsByFaculty(String inputFaculty)
    {      
        List<ReservationEntry> resultsReservationList = null;
 
        try
        {
            psRoomReservationsByFaculty = connection.prepareStatement("select * from RESERVATIONS where FACULTY = ? order by DATE, TIMESTAMP");
            psRoomReservationsByFaculty.setString(1, inputFaculty);
            resultSet = psRoomReservationsByFaculty.executeQuery();
            
            resultsReservationList = new ArrayList<ReservationEntry>();
            
            while (resultSet.next() == true)
            {
                resultsReservationList.add(new ReservationEntry( resultSet.getString("FACULTY"), resultSet.getString("ROOM"), resultSet.getDate("DATE"), resultSet.getInt("SEATS"), resultSet.getTimestamp("TIMESTAMP") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsReservationList;
    }   

    public static List<ReservationEntry> getAllFacultyReservations()
    {      
        List<ReservationEntry> resultsReservationList = null;
 
        try
        {
            psFacultyReservations = connection.prepareStatement("select * from RESERVATIONS order by DATE, TIMESTAMP");
            resultSet = psFacultyReservations.executeQuery();
            
            resultsReservationList = new ArrayList<ReservationEntry>();
            
            while (resultSet.next() == true)
            {
                resultsReservationList.add(new ReservationEntry( resultSet.getString("FACULTY"), resultSet.getString("ROOM"), resultSet.getDate("DATE"), resultSet.getInt("SEATS"), resultSet.getTimestamp("TIMESTAMP") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsReservationList;
    }   

    public static List<ReservationEntry> getReservationsByRoom(String inputRoomName)
    {      
        List<ReservationEntry> resultsReservationList = null;
 
        try
        {
            psReservationsByRoom = connection.prepareStatement("select * from RESERVATIONS where ROOM = ? order by DATE, TIMESTAMP");
            psReservationsByRoom.setString(1, inputRoomName);
            resultSet = psReservationsByRoom.executeQuery();
            
            resultsReservationList = new ArrayList<ReservationEntry>();
            
            while (resultSet.next() == true)
            {
                resultsReservationList.add(new ReservationEntry( resultSet.getString("FACULTY"), resultSet.getString("ROOM"), resultSet.getDate("DATE"), resultSet.getInt("SEATS"), resultSet.getTimestamp("TIMESTAMP") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsReservationList;
    }   

    //// check() Methods
    
    public static Boolean checkReservationsByFaculty(String inputFaculty, String inputDate)
    {      
        //connection = DBConnection.getConnection();
        try
        {
            psCheckFacultyReservation = connection.prepareStatement("select FACULTY from RESERVATIONS where FACULTY = ? and DATE = ? ");
            psCheckFacultyReservation.setString(1, inputFaculty);
            psCheckFacultyReservation.setString(2, inputDate);
            resultSet = psCheckFacultyReservation.executeQuery();
            
            // If true, then Faculty already has a room Reserved on this date
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
