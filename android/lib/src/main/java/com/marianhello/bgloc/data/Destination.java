package com.marianhello.bgloc.data;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.TimeUtils;

import org.json.JSONObject;
import org.json.JSONException;

public class Destination implements Parcelable {
    private double latitude = 0.0;
    private double longitude = 0.0;
    private double radius = 0.0;

    public Destination() {}

    public Destination(double latitude, double longitude, double radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    /**
     * Construct a new Location object that is copied from an existing one.
     * @param location
     */
    public Destination(Destination l) {
        latitude = l.latitude;
        longitude = l.longitude;
        radius = l.radius;
    }

    private Destination(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        radius = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeFloat(radius);
    }

    public static final Parcelable.Creator<Destination> CREATOR
            = new Parcelable.Creator<Destination>() {
        public Destination createFromParcel(Parcel in) {
            return new Destination(in);
        }
        public Destination[] newArray(int size) {
            return new Destination[size];
        }
    };
    
    public Destination makeClone() {
        return new Destination(this);
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        this.hasRadius = true;
    }
    

    /** Determines whether the location is inside the destination
     *
     * Origin: http://www.movable-type.co.uk/scripts/latlong.html
     *
     * @param destination  The new Destination that you want to evaluate
     * @param location  The new Location that you want to evaluate
     */
    public static double containsLocation(Destination destination BackgroundLocation location) {
      double R = 6371000.0;
      double x = (location.getLongitude() - destination.getLongitude()) * Math.cos((destination.getLatitude() + location.getLatitude()) / 2);
      double y = (location.getLatitude() - destination.getLatitude());
      return Math.sqrt(x*x + y*y) * R;
    }

    @Override
    public String toString () {
        StringBuilder s = new StringBuilder();
        s.append("Destination[").append(provider);
        s.append(String.format(" %.6f,%.6f", latitude, longitude));
        if (hasRadius) s.append(" radius=").append(radius);
        s.append("]");

        return s.toString();
    }

    /**
     * Returns location as JSON object.
     * @throws JSONException
     */
    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("latitude", latitude);
        json.put("longitude", longitude);
        json.put("radius", radius);
        return json;
  	}
}
