package com.androidside.intentdemob2;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonInfo implements Parcelable {
    public String name;
    public String age;
    
    public PersonInfo() {        
    }
    
    public PersonInfo(Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);
        this.name = data[0];
        this.age = data[1];
    }
    
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeStringArray(new String[]{this.name, this.age});
    }
    
    @SuppressWarnings("unchecked")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PersonInfo createFromParcel(Parcel in) {
            return new PersonInfo(in);
        }

        public PersonInfo[] newArray(int size) {
            return new PersonInfo[size];
        }
    };
    
}
