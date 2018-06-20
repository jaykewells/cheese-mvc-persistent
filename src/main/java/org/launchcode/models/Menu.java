package org.launchcode.models;

import com.sun.javafx.beans.IDProperty;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private int id;

    String name;

    @ManyToMany
    private List<Cheese> cheeses = new ArrayList<>();

    public Menu(){}

    public Menu(String aName){
        this.name = aName;
    }

    public void addItem(Cheese item){
        cheeses.add(item);
    }

    public List<Cheese> getCheeses(){ return cheeses;}

    public String getName() {return this.name;}

    public void setName(String aName){ this.name = aName;}

    public int getId(){ return this.id;}

}
