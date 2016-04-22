package com.example.serega.lab4dbperson09042016;

/**
 * Created by Serega on 09.04.2016.
 */
public class Person {
    public Person(long id, String name, int mark ){
        _id=id;
        _name=name;
        _mark=mark;
    }

    private long _id;
    private String _name;
    private int _mark;

    public long get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public int get_mark() {
        return _mark;
    }
}
