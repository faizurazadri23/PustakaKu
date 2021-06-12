package com.faizurazadri.pustakaku.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AuthorModel implements Parcelable {

    @SerializedName("Id_pengarang")
    private int Id_pengarang;

    @SerializedName("nama_pengarang")
    private String nama_pengarang;

    @SerializedName("email")
    private String email;

    @SerializedName("notelp")
    private String notelp;

    @SerializedName("alamat")
    private String alamat;


    protected AuthorModel(Parcel in) {
        Id_pengarang = in.readInt();
        nama_pengarang = in.readString();
        email = in.readString();
        notelp = in.readString();
        alamat = in.readString();
    }

    public static final Creator<AuthorModel> CREATOR = new Creator<AuthorModel>() {
        @Override
        public AuthorModel createFromParcel(Parcel in) {
            return new AuthorModel(in);
        }

        @Override
        public AuthorModel[] newArray(int size) {
            return new AuthorModel[size];
        }
    };

    public int getId_pengarang() {
        return Id_pengarang;
    }

    public void setId_pengarang(int id_pengarang) {
        Id_pengarang = id_pengarang;
    }

    public String getNama_pengarang() {
        return nama_pengarang;
    }

    public void setNama_pengarang(String nama_pengarang) {
        this.nama_pengarang = nama_pengarang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id_pengarang);
        dest.writeString(nama_pengarang);
        dest.writeString(email);
        dest.writeString(notelp);
        dest.writeString(alamat);
    }
}
