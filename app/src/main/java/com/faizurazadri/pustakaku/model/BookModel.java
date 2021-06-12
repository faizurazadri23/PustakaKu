package com.faizurazadri.pustakaku.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BookModel implements Parcelable {

    @SerializedName("Id_buku")
    private int Id_buku;

    @SerializedName("Judul_buku")
    private String Judul_buku;

    @SerializedName("Id_pengarang")
    private int Id_pengarang;

    @SerializedName("Jml_halaman")
    private int Jml_halaman;

    @SerializedName("nomor_isbn")
    private String nomor_isbn;

    @SerializedName("Tahun_terbit")
    private String Tahun_terbit;

    @SerializedName("nama_pengarang")
    private String nama_pengarang;

    @SerializedName("email")
    private String email;

    @SerializedName("notelpn")
    private String notelpn;

    @SerializedName("alamat")
    private String alamat;

    protected BookModel(Parcel in) {
        Id_buku = in.readInt();
        Judul_buku = in.readString();
        Id_pengarang = in.readInt();
        Jml_halaman = in.readInt();
        nomor_isbn = in.readString();
        Tahun_terbit = in.readString();
        nama_pengarang = in.readString();
        email = in.readString();
        notelpn = in.readString();
        alamat = in.readString();
    }

    public static final Creator<BookModel> CREATOR = new Creator<BookModel>() {
        @Override
        public BookModel createFromParcel(Parcel in) {
            return new BookModel(in);
        }

        @Override
        public BookModel[] newArray(int size) {
            return new BookModel[size];
        }
    };

    public int getId_buku() {
        return Id_buku;
    }

    public void setId_buku(int id_buku) {
        Id_buku = id_buku;
    }

    public String getJudul_buku() {
        return Judul_buku;
    }

    public void setJudul_buku(String judul_buku) {
        Judul_buku = judul_buku;
    }

    public int getId_pengarang() {
        return Id_pengarang;
    }

    public void setId_pengarang(int id_pengarang) {
        Id_pengarang = id_pengarang;
    }

    public int getJml_halaman() {
        return Jml_halaman;
    }

    public void setJml_halaman(int jml_halaman) {
        Jml_halaman = jml_halaman;
    }

    public String getNomor_isbn() {
        return nomor_isbn;
    }

    public void setNomor_isbn(String nomor_isbn) {
        this.nomor_isbn = nomor_isbn;
    }

    public String getTahun_terbit() {
        return Tahun_terbit;
    }

    public void setTahun_terbit(String tahun_terbit) {
        Tahun_terbit = tahun_terbit;
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

    public String getNotelpn() {
        return notelpn;
    }

    public void setNotelpn(String notelpn) {
        this.notelpn = notelpn;
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
        dest.writeInt(Id_buku);
        dest.writeString(Judul_buku);
        dest.writeInt(Id_pengarang);
        dest.writeInt(Jml_halaman);
        dest.writeString(nomor_isbn);
        dest.writeString(Tahun_terbit);
        dest.writeString(nama_pengarang);
        dest.writeString(email);
        dest.writeString(notelpn);
        dest.writeString(alamat);
    }
}
