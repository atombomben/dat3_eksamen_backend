/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos.carRace;

import entities.Driver;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peter
 */
public class DriverDTO {
    
    private Long id;
    private String name;
    private String birthYear;
    private String gender;
    CarDTO car;
    
    public DriverDTO(String name, String birthYear, String gender) {
        this.name = name;
        this.birthYear = birthYear;
        this.gender = gender;
    }
    
     public DriverDTO(Driver d) {
        if (d.getId() != null) this.id = d.getId();
        this.name = d.getName();
        this.birthYear = d.getBirthYear();
        this.gender = d.getGender();
        this.car = new CarDTO(d.getCar());
    }
        
        public static List<DriverDTO> getDTOs(List<Driver> drivers){
        List<DriverDTO> dtos = new ArrayList();
        drivers.forEach(driver->dtos.add(new DriverDTO(driver)));
        return dtos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }
    
    
}
