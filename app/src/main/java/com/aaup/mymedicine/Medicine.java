package com.aaup.mymedicine;

public class Medicine {

    private String name;
    private String desc;

    public Medicine() {
        // Constructor required for Firebase Database
    }

    public Medicine(String name, String desc) {
       this.name = name;
       this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

}