package com.example.devizavlt.api.model;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Item {

    @Element
    private String bank;

    @Element
    String datum ;

    @Element
    private String penznem;

    @Element(required = false)
    private double vetel;

    @Element(required = false)
    private double eladas;

    @Element(required = false)
    private double kozep;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getPenznem() {
        return penznem;
    }

    public void setPenznem(String penznem) {
        this.penznem = penznem;
    }

    public double getVetel() {
        return vetel;
    }

    public double getKozep() {
        return kozep;
    }

    public void setKozep(double kozep) {
        this.kozep = kozep;
    }

    public void setVetel(double vetel) {
        this.vetel = vetel;
    }

    public double getEladas() {
        return eladas;
    }

    public void setEladas(double eladas) {
        this.eladas = eladas;
    }

    @Override
    public String toString() {
        return "Item{" +
                "bank='" + bank + '\'' +
                ", datum='" + datum + '\'' +
                ", penznem='" + penznem + '\'' +
                ", vetel=" + vetel +
                ", eladas=" + eladas +
                ", kozep=" + kozep +
                '}';
    }
}
