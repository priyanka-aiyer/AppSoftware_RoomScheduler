/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// RoomEntry Class that represents the ROOMS table in the repository
public class RoomEntry {
    
    private String name;
    private int seats;
    
    // constructor
    public RoomEntry() {   
    }

    // constructor
    public RoomEntry(String name, int seats) {   
        setName(name);
        setSeats(seats);
    }

    ////////////////////////////////////////
    //////// setter Methods

    public void setName(String name) {
        this.name = name;
    }
        
    public void setSeats(int seats) {
        this.seats = seats;
    }

    ////////////////////////////////////////
    //////// getter Methods
    
    public String getName() {
        return name;
    }
    
    public int getSeats() {
        return seats;
    }
}
