package com.ronny.k24.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelBiodata implements Parcelable {
    String idUser,namaUser,kodeMember,tglUser,almtUser,jenKel,userName,passUser;
    int statusDb;

    public ModelBiodata( String namaUser, String kodeMember, String tglUser, String almtUser, String jenKel, String userName, String passUser, int statusDb) {
        this.namaUser = namaUser;
        this.kodeMember = kodeMember;
        this.tglUser = tglUser;
        this.almtUser = almtUser;
        this.jenKel = jenKel;
        this.userName = userName;
        this.passUser = passUser;
        this.statusDb = statusDb;
    }

    public ModelBiodata(){

    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getKodeMember() {
        return kodeMember;
    }

    public void setKodeMember(String kodeMember) {
        this.kodeMember = kodeMember;
    }

    public String getTglUser() {
        return tglUser;
    }

    public void setTglUser(String tglUser) {
        this.tglUser = tglUser;
    }

    public String getAlmtUser() {
        return almtUser;
    }

    public void setAlmtUser(String almtUser) {
        this.almtUser = almtUser;
    }

    public String getJenKel() {
        return jenKel;
    }

    public void setJenKel(String jenKel) {
        this.jenKel = jenKel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassUser() {
        return passUser;
    }

    public void setPassUser(String passUser) {
        this.passUser = passUser;
    }

    public int getStatusDb() {
        return statusDb;
    }

    public void setStatusDb(int statusDb) {
        this.statusDb = statusDb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idUser);
        dest.writeString(this.namaUser);
        dest.writeString(this.kodeMember);
        dest.writeString(this.tglUser);
        dest.writeString(this.almtUser);
        dest.writeString(this.jenKel);
        dest.writeString(this.userName);
        dest.writeString(this.passUser);
        dest.writeInt(this.statusDb);
    }

    protected ModelBiodata(Parcel in) {
        this.idUser = in.readString();
        this.namaUser = in.readString();
        this.kodeMember = in.readString();
        this.tglUser = in.readString();
        this.almtUser = in.readString();
        this.jenKel = in.readString();
        this.userName = in.readString();
        this.passUser = in.readString();
        this.statusDb = in.readInt();
    }

    public static final Creator<ModelBiodata> CREATOR = new Creator<ModelBiodata>() {
        @Override
        public ModelBiodata createFromParcel(Parcel source) {
            return new ModelBiodata(source);
        }

        @Override
        public ModelBiodata[] newArray(int size) {
            return new ModelBiodata[size];
        }
    };
}