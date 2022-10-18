import java.util.Date;
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// ReservationEntry Class that represents the RESERVATIONS table in the repository
public class ReservationEntry {
    
    private String faculty;
    private String room;
    private Date ddate = new Date();
    private int seats;
    private Timestamp ttimestamp = new Timestamp(ddate.getTime());
    
    // constructor
    public ReservationEntry() {   
    }

    // constructor
    public ReservationEntry(String faculty, String room, Date ddate, int seats, Timestamp ttimestamp) {   
        setFaculty(faculty);
        setRoom(room);
        setDdate(ddate);
        setSeats(seats);
        setTtimestamp(ttimestamp);
    }

    ////////////////////////////////////////
    //////// setter Methods

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
        
    public void setRoom(String room) {
        this.room = room;
    }
        
    public void setDdate(Date ddate) {
        this.ddate = ddate;
    }
    
    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setTtimestamp(Timestamp ttimestamp) {
        this.ttimestamp = ttimestamp;
    }

    
    ////////////////////////////////////////
    //////// getter Methods
    
    public String getFaculty() {
        return faculty;
    }
    
    public String getRoom() {
        return room;
    }
    
    public Date getDdate() {
        return ddate;
    }
    
    public int getSeats() {
        return seats;
    }
    
    public Timestamp getTtimestamp() {
        return ttimestamp;
    }
 }
