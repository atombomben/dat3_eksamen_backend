/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos.carRace;

import entities.Race;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peter
 */
public class RaceDTO {
    
    private Long id;
    private String name;
    private String date;
    private String time;
    private String location;

    public RaceDTO(String name, String date, String time, String location) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
    }
    
        public RaceDTO(Race r) {
        if (r.getId() != null) this.id = r.getId();
        this.name = r.getName();
        this.date = r.getDate();
        this.time = r.getTime();
        this.location = r.getLocation();
    }
        
        public static List<RaceDTO> getDTOs(List<Race> races){
        List<RaceDTO> dtos = new ArrayList();
        races.forEach(race->dtos.add(new RaceDTO(race)));
        return dtos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
    
}
