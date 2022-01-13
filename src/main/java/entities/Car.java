/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author peter
 */
@Entity
@NamedQuery(name = "Car.deleteAllRows", query = "DELETE from Car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    String brand;
    String make;
    String year;
    
    @OneToMany(mappedBy = "car", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Driver> drivers;
    
    @ManyToMany(mappedBy = "cars")
    private List<Race> races;

    public Car() {
    }

    public Car(String name, String brand, String make, String year) {
        this.name = name;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.drivers = new ArrayList<>();
        this.races = new ArrayList<>();
    }
    
      public List<Race> getRace() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }
    
       public void addRace(Race race){
            this.races.add(race);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    


    
}
