
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Faculty {

    private String name;
    
    // constructor
    public Faculty() {   
    }
    
    // constructor
    public Faculty(String name) {   
        setName(name);
    }

    ////////////////////////////////////////
    //////// setter Methods

    public void setName(String name) {
        this.name = name;
    }
        
    ////////////////////////////////////////
    //////// getter Methods

    public String getName() {
        return name;
    }

}
