package com.example.myapplication.Retrofits;

import com.example.myapplication.products;

import org.json.JSONArray;
import org.json.JSONStringer;

import java.util.List;

import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface INodeJS {

        @POST("register")
        @FormUrlEncoded
    Observable <String> registerUser (@Field("firstname") String firstname ,
                                   @Field("lastname")String lastname ,
                                   @Field("email") String email ,
                                   @Field("password") String password);
    @POST("login")
    @FormUrlEncoded
    Observable <String>  loginUser(@Field("email") String email ,
                         @Field("password") String password);

    @POST("contact")
    @FormUrlEncoded
    Observable <String>  contactUser(@Field("name") String firstname ,
                           @Field("email") String email ,
                           @Field("phone") int phone ,
                           @Field("description") String description);

    @POST("home")
    @FormUrlEncoded
    Observable <String>  homeApp(@Field("") String s );

    @POST("profile")
    @FormUrlEncoded
    Observable <String>  profileuser(@Field("id") String id );

    @POST("addcard")
    @FormUrlEncoded
    Observable <String> addcard (@Field("IdCli") String IdCli ,
                                 @Field("idProd") String idProd ,
                                 @Field("title") String title ,
                                 @Field("description") String description ,
                                 @Field("price") String price ,
                                 @Field("imglink") String imglink ,
                                 @Field("qte") int qte );


    @POST("card")
    @FormUrlEncoded
    Observable <String>  card(@Field("idcli") String idcli );

    @POST("order")
    @FormUrlEncoded
    Observable <String>  card2(@Field("idcli") String idcli );

    @POST("del")
    @FormUrlEncoded
    Observable <String>  del(@Field("idcli") String idcli );

    @POST("delone")
    @FormUrlEncoded
    Observable <String>  delone(@Field("idcard") String idcard );

    @POST("adadr")
    @FormUrlEncoded
    Observable <String>  adadr(@Field("id") String id ,
                               @Field("adrs") String adrs);

    @POST("insorders")
    @FormUrlEncoded
    Observable <String> insorders (@Field("IdCli") String IdCli ,
                                 @Field("idProd") String idProd ,
                                 @Field("title") String title ,
                                 @Field("description") String description ,
                                 @Field("price") String price ,
                                 @Field("imglink") String imglink ,
                                 @Field("qte") int qte,
                                 @Field("confirmation") String confirm,
                                 @Field("adress") String adress);

    @POST("updpass")
    @FormUrlEncoded
    Observable <String>  updpass(@Field("id") String id ,
                                   @Field("opass") String opass ,
                                   @Field("npass") String npass);

    @POST("shopcard")
    @FormUrlEncoded
    Observable <String>  shopcard(@Field("idcli") String idcli );


    @POST("gid")
    @FormUrlEncoded
    Observable <String>  gid(@Field("email") String email );


    @POST("mqte")
    @FormUrlEncoded
    Observable <String>  mqte(@Field("id") String id ,
                               @Field("qte") int qte);


}

