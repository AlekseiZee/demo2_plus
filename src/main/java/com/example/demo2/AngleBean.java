package com.example.demo2;

public class AngleBean {
    Integer id;
    Integer id_AnglePair;
    Double hangle;
    Double vangle;

    public AngleBean() {
    }

    public AngleBean(Integer id, Integer id_AnglePair, Double hangle, Double vangle) {
        this.id = id;
        this.id_AnglePair = id_AnglePair;
        this.hangle = hangle;
        this.vangle = vangle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_AnglePair() {
        return id_AnglePair;
    }

    public void setId_AnglePair(Integer id_AnglePair) {
        this.id_AnglePair = id_AnglePair;
    }

    public Double getHangle() {
        return hangle;
    }

    public void setHangle(Double hangle) {
        this.hangle = hangle;
    }

    public Double getVangle() {
        return vangle;
    }

    public void setVangle(Double vangle) {
        this.vangle = vangle;
    }
}
