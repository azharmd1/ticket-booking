package org.example.entities;


import java.sql.Time;
import java.util.List;
import java.util.Map;

public class Train {
    private String trainId;
    private String trainNo;
    private List<List<Integer>> seats;
    private Map<String, String> timeTable;
    private List<String> stations;

    public Train(String trainId, String trainNo, List<List<Integer>> seats, Map<String, String> timeTable, List<String> stations) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.timeTable = timeTable;
        this.stations = stations;
    }

    public Train() {

    }


    public String getTrainId() {
        return trainId;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public Map<String, String> getTimeTable() {
        return timeTable;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    public void setTimeTable(Map<String, String> timeTable) {
        this.timeTable = timeTable;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }
    public String getTrainInfo(){
        return String.format("Train ID: %s Train No: %s", trainId, trainNo);
    }

    public Map<String, String> getStationTimes() {
        return timeTable;
    }
}
