package com.msi.manning.windwaves.data;

import java.util.Date;

public class BuoyData {

    public Date date;
    public String dateString;
    public String title;
    public String description;
    public String location;
    public String windDirection;
    public String windSpeed;
    public String windGust;
    public String waveHeight;
    public String wavePeriod;
    public String atmoPressure;
    public String atmoPressureTendency;
    public String airTemp;
    public String waterTemp;
    public String link;
    public String geoRssPoint;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BuoyData - " + this.title);
        sb.append("\ndate - " + this.date);
        sb.append("\ndateString - " + this.dateString);
        sb.append("\nlink - " + this.link);
        sb.append("\ngeoRssPoint - " + this.geoRssPoint);
        sb.append("\ndescription - " + this.description);
        sb.append("\nlocation - " + this.location);
        sb.append("\nwindDirection - " + this.windDirection);
        sb.append("\nwindSpeed - " + this.windSpeed);
        sb.append("\nwindGust - " + this.windGust);
        sb.append("\nwaveHeight - " + this.waveHeight);
        sb.append("\nwavePeriod - " + this.wavePeriod);
        sb.append("\natmoPressure - " + this.atmoPressure);
        sb.append("\natmoPressureTendency - " + this.atmoPressureTendency);
        sb.append("\nairTemp - " + this.airTemp);
        sb.append("\nwaterTemp - " + this.waterTemp);
        return sb.toString();
    }
}
