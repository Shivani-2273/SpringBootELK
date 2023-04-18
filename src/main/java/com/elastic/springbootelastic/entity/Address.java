package com.elastic.springbootelastic.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

//@Document(indexName = "address")
public class Address  {
//    @Id
//    @Field(type = FieldType.Keyword)
    private int id;
  //  @Field(type = FieldType.Keyword)
    private String area;
   // @Field(type = FieldType.Keyword)
    private String city;
   // @Field(type = FieldType.Keyword)
    private String state;
    //@Field(type = FieldType.Keyword)
    private Long pincode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public Address(int id, String area, String city, String state, Long pincode) {
        this.id = id;
        this.area = area;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public Address() {
    }
}
