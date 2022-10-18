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

public class RoomQueries {
    private static Connection connection;
    private static PreparedStatement psGetRoomsList;
    private static PreparedStatement psGetRoom;
    private static PreparedStatement psGetRoomsForSeats;
    private static PreparedStatement psGetRoomCapacityForSeats;
    private static PreparedStatement psAddRoom;
    private static PreparedStatement psGetRoomName;
    private static PreparedStatement psdropRoom;    
    private static ResultSet resultSet;
    
    
    //// constructor
    public RoomQueries() {   
        connection = DBConnection.getConnection();
    }
    
    ////////
    public static void addRoom(String inputRoomName, int inputSeats)
    {
        try
        {
            psAddRoom = connection.prepareStatement("insert into ROOMS (NAME, SEATS) values (?, ?)");
            psAddRoom.setString(1, inputRoomName);
            psAddRoom.setInt(2, inputSeats);
            psAddRoom.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }        
    }
    
    public static void deleteRoom(String inputRoomName)
    {
        try
        {
            psdropRoom = connection.prepareStatement("delete from ROOMS where NAME = ? ");
            psdropRoom.setString(1, inputRoomName);
            psdropRoom.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }        
    }

    //// get() Methods
            
    public List<RoomEntry> getAllPossibleRooms()
    {
        List<RoomEntry> resultsRoomsList = null;

        try
        {
            psGetRoomsList = connection.prepareStatement("select * from ROOMS order by NAME");
            resultSet = psGetRoomsList.executeQuery();
            
            resultsRoomsList = new ArrayList<RoomEntry>();

            while(resultSet.next())
            {
                resultsRoomsList.add(new RoomEntry( resultSet.getString("NAME"), resultSet.getInt("SEATS") ));                        
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return resultsRoomsList;
    }
    
    public static String getBestFitRoom(String inputSeats, ArrayList<String> roomReservedList)
    {      
        RoomEntry roomEntry = null;

        int totalRoomsReserved = roomReservedList.size();

        String bestFitRoom = "";        
        Boolean exitFlag = false;

        try
        {           
            // Fetch * from ROOMS table | where the columns SEATS is > than the entered-Seats-count | Order by SEATS i.e. ASC Order
            psGetRoom = connection.prepareStatement("select * from ROOMS where SEATS >= ? order by SEATS ");
            psGetRoom.setString(1, inputSeats);
            resultSet = psGetRoom.executeQuery();    
            
            while (resultSet.next())
            {
                exitFlag = false;

                // Consider the FIRST fetched record for Reservation as Best-Fit option
                roomEntry = new RoomEntry( resultSet.getString("NAME"), resultSet.getInt("SEATS") );
                if (totalRoomsReserved == 0) {
                    bestFitRoom = roomEntry.getName();
                    break;
                }

                // check if the suitable room is already reserved?
                for (int i = 0; i < totalRoomsReserved; i++) {
                    if (roomEntry.getName().equals(roomReservedList.get(i))) {
                        exitFlag = true;
                        break;
                    }
                }    
                if (exitFlag != true) {
                    bestFitRoom = roomEntry.getName();
                    break;
                }    
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return bestFitRoom;
    }
    
    //// check() Methods
    
    public static Boolean checkRoomForSeats(String inputSeats)
    {

        try
        {
            psGetRoomsForSeats = connection.prepareStatement("select * from ROOMS where SEATS >= ? order by NAME");
            psGetRoomsForSeats.setString(1, inputSeats);
            resultSet = psGetRoomsForSeats.executeQuery();
            
            if (resultSet.next())
                return true;
            else
                System.out.println("NO rooms exist for the requested capacity of Seats");
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return false;
    }

    public static Boolean checkRoomCapacityForSeats(String roomName, String requestedSeats)
    {
        try
        {
            psGetRoomCapacityForSeats = connection.prepareStatement("select * from ROOMS where NAME = ? and SEATS >= ? order by NAME");
            psGetRoomCapacityForSeats.setString(1, roomName);
            psGetRoomCapacityForSeats.setString(2, requestedSeats);
            resultSet = psGetRoomCapacityForSeats.executeQuery();
            
            if (resultSet.next())
                return true;
            else
                System.out.println("NO rooms exist for the requested capacity of Seats ");
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return false;
    }

    public static Boolean checkRoomByName(String inputRoomName)
    {
        try
        {
            psGetRoomName = connection.prepareStatement("select NAME from ROOMS where NAME = ? order by NAME ");
            psGetRoomName.setString(1, inputRoomName);
            resultSet = psGetRoomName.executeQuery();
            
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
