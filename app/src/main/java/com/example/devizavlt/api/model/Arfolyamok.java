package com.example.devizavlt.api.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class Arfolyamok {

    @ElementList
    protected List<Item> valuta;

    @ElementList
    protected List<Item> deviza;






    public List<Item> getValuta() {
        valuta.addAll(deviza);

        return valuta;
    }
}



