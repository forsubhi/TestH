package house.find.com.housefinder2;

import java.util.Date;

/**
 * Created by MohammedSubhi on 1/11/2015.
 */
public class Person {

     Date birth_day ;
    String device_id;
    String email ;
    String facebook_link;
    String first_name ;
    String gender;
    String last_name;
   // String location ;
    byte[] picture;

    String relation_ship_state ;

    public Date getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(Date birth_day) {
        this.birth_day = birth_day;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook_link() {
        return facebook_link;
    }

    public void setFacebook_link(String facebook_link) {
        this.facebook_link = facebook_link;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getRelation_ship_state() {
        return relation_ship_state;
    }

    public void setRelation_ship_state(String relation_ship_state) {
        this.relation_ship_state = relation_ship_state;
    }
}
