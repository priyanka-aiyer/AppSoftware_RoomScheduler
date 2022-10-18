import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Dates {
    
    private Date ddate = new Date();

    // constructor
    public Dates() {   
    }

    // constructor
    public Dates(Date ddate) {   
        setDdate(ddate);
    }
    ////////////////////////////////////////
    //////// setter Methods

    public void setDdate(Date ddate) {
        this.ddate = ddate;
    }

    ////////////////////////////////////////
    //////// getter Methods
    
    public Date getDdate() {
        return ddate;
    }
    
}
