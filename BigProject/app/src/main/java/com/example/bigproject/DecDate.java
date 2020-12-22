package com.example.bigproject;

import java.util.Comparator;

public class DecDate implements Comparator<Pictures> {
    @Override
    public int compare(Pictures o1, Pictures o2) {
        if(o1.getdate().compareTo(o2.getdate())==0) return 0;
        else if(o1.getdate().compareTo(o2.getdate())<0) return 1;
        else return -1;
    }
}
