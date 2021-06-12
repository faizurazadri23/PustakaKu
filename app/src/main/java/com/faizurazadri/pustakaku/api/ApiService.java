package com.faizurazadri.pustakaku.api;

import com.faizurazadri.pustakaku.model.GetAuthor;
import com.faizurazadri.pustakaku.model.GetBook;
import com.faizurazadri.pustakaku.model.PostPutDelAuthor;
import com.faizurazadri.pustakaku.model.PostPutDelBook;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {


    @GET("Pengarang")
    Call<GetAuthor> getDataAuthor();

    @GET("Buku")
    Call<GetBook> getDataBook();

    @FormUrlEncoded
    @POST("buku")
    Call<PostPutDelBook> postBook(
            @Field("Judul_buku") String Judul_buku,
            @Field("Id_pengarang") int Id_pengarang,
            @Field("Jml_halaman") int Jml_halaman,
            @Field("nomor_isbn") String nomor_isbn,
            @Field("Tahun_terbit") String Tahun_terbit);

    @FormUrlEncoded
    @POST("pengarang")
    Call<PostPutDelAuthor> postAuthor(
            @Field("nama_pengarang") String nama_pengarang,
            @Field("email") String email,
            @Field("notelp") String notelp,
            @Field("alamat") String alamat
    );

    @FormUrlEncoded
    @PUT("buku/{id}")
    Call<PostPutDelBook> putBook(
            @Path("id") int id,
            @Field("Judul_buku") String Judul_buku,
            @Field("Id_pengarang") int Id_pengarang,
            @Field("Jml_halaman") int Jml_halaman,
            @Field("nomor_isbn") String nomor_isbn,
            @Field("Tahun_terbit") String Tahun_terbit);

    @FormUrlEncoded
    @PUT("pengarang/{id}")
    Call<PostPutDelAuthor> putAuthor(
            @Path("id") int id,
            @Field("nama_pengarang") String nama_pengarang,
            @Field("email") String email,
            @Field("notelp") String notelp,
            @Field("alamat") String alamat
    );

    @DELETE("buku/{id}")
    Call<PostPutDelBook> delBook (@Path("id") int id);

    @DELETE("pengarang/{id}")
    Call<PostPutDelAuthor> delAuthor (@Path("id") int id);

}
