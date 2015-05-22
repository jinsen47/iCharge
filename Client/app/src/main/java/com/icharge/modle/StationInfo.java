package com.icharge.modle;

/**
 * Created by Administrator on 2015/3/20.
 */
public class StationInfo {


    String stationName ="";
    String stationAddress="";
    String distance ="";
    String telNumber ="";
    int score=0;
    PileInfo pf ;


    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public PileInfo getPf() {
        return pf;
    }

    public void setPf(PileInfo pf) {
        this.pf = pf;
    }



}
