package com.macrobios.earthquakemonitor;

import java.util.Objects;

public class Earthquake {

    private String id;
    private String place;
    private double magnitud;
    private long time;
    private double latiitude;
    private double longitude;

    public Earthquake(String id, String place, double magnitud, long time, double latiitude, double longitude) {
        this.id = id;
        this.place = place;
        this.magnitud = magnitud;
        this.time = time;
        this.latiitude = latiitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public double getMagnitud() {
        return magnitud;
    }

    public long getTime() {
        return time;
    }

    public double getLatiitude() {
        return latiitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earthquake that = (Earthquake) o;
        return Double.compare(that.magnitud, magnitud) == 0 &&
                time == that.time &&
                Double.compare(that.latiitude, latiitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                id.equals(that.id) &&
                place.equals(that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, place, magnitud, time, latiitude, longitude);
    }
}
