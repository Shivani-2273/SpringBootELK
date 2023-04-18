package com.elastic.springbootelastic.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "customer",createIndex = true)
public class Customer {

    @Id
    private int id;
    @Field(type = FieldType.Text)
    private String fname;
    @Field(type = FieldType.Text)
    private String lname;
    @Field(type = FieldType.Long)
    private int age;

    @Field(type = FieldType.Nested) //this is join one to one only
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getAge() {
        return age;
    }

//    public Customer(int id, String fname, String lname, int age,Address address) {
//        this.id = id;
//        this.fname = fname;
//        this.lname = lname;
//        this.age = age;
//        this.address = address;
//    }

    public void setAge(int age) {
        this.age = age;
    }

    public Customer() {
    }
}
