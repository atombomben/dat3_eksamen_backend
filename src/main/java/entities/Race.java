/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dtos.carRace.RaceDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

/**
 *
 * @author peter
 */
@Entity
@NamedQuery(name = "Race.deleteAllRows", query = "DELETE from Race")
public class Race implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String date;
    private String time;
    private String location;
    
    
    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REMOVE
    })
        @JoinTable(name = "CAR_RACE",
        joinColumns = @JoinColumn(name = "races_id"),
        inverseJoinColumns = @JoinColumn(name = "cars_id")
    )
    private List<Car> cars;
     
    public Race() {
    }

    public Race(String name, String date, String time, String location) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.cars = new ArrayList<>();
    }
    
    public Race(RaceDTO raceDTO) {
       if (raceDTO.getId() != null) this.id = raceDTO.getId();
       this.name = raceDTO.getName();
       this.date = raceDTO.getDate();
       this.time = raceDTO.getTime();
       this.location = raceDTO.getLocation();
   }
    
       public List<Car> getCars() {
        return cars;
    }

       public void setCar(List<Car> car) {
        this.cars = cars;
    }
    
       public void addCar(Car car){
            this.cars.add(car);
    }
    
       public void removeCars(Car cars){
        if (cars != null){
            this.cars.remove(cars);  
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String Date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}
