package com.example.texttoemotion.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User  implements Parcelable {
    private String Name;
    private String id;
    private String email;
    private String password;
    private String ssn;
    private long date_birth;
    private String type;

    public User() {
    }

    public User(String name, String id, String email,String password, String ssn, long date_birth) {
        Name = name;
        this.id = id;
        this.email = email;
        this.password=password;
        this.ssn = ssn;
        this.date_birth = date_birth;
        this.type="user";
    }

    protected User(Parcel in) {
        Name = in.readString();
        id = in.readString();
        email = in.readString();
        password = in.readString();
        ssn = in.readString();
        date_birth = in.readLong();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(ssn);
        dest.writeLong(date_birth);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSsn() {
        return ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public long getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(long date_birth) {
        this.date_birth = date_birth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
