package house.find.com.housefinder2;

import android.view.VelocityTracker;

/**
 * Created by MohammedSubhi on 1/15/2015.
 */
public class City {
    String city = "";
    String province = "";
    int rank;

    public City(String city, String province, int rank) {
        this.city = city;
        this.province = province;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return city;
    }
}
