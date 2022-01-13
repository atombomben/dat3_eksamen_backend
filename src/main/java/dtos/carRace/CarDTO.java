/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos.carRace;

import entities.Car;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peter
 */
public class CarDTO {
    
    private Long id;
    private String name;
    private String brand;
    private String make;
    private String year;
    List<DriverDTO> drivers;
    List<RaceDTO> races;
    
    public CarDTO(String name, String brand, String make, String year) {
        this.name = name;
        this.brand = brand;
        this.make = make;
        this.year = year;
    }

    public CarDTO(Car c) {
        if (c.getId() != null) this.id = c.getId();
        this.name = c.getName();
        this.brand = c.getBrand();
        this.make = c.getMake();
        this.year = c.getYear();
        this.races = RaceDTO.getDTOs(c.getRace());
    }
        
        public static List<CarDTO> getDTOs(List<Car> cars){
        List<CarDTO> dtos = new ArrayList();
        cars.forEach(car->dtos.add(new CarDTO(car)));
        return dtos;
    }

    public Long getId() {
        return id;
    }

    public List<DriverDTO> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverDTO> drivers) {
        this.drivers = drivers;
    }

    public List<RaceDTO> getRaces() {
        return races;
    }

    public void setRaces(List<RaceDTO> races) {
        this.races = races;
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
