package com.ezbus.client;



import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

public class Pass extends Item {

    private String name;
    private String companyId;
    private String type;
    private String city;
    private String price;
    private String id;



    public Pass(String companyId, EditText name, EditText type, EditText city, EditText price /*Date date*/) {
        //this.name = name;
        super.id = generateId();
        this.companyId = companyId;
        //this.price = price;
        //super.expiration = date;
        //this.type = type;
        //this.city = city;
        this.id = UUID.randomUUID().toString();
    }

    public String getName() { return name; }

    public String getCompanyId() { return companyId;}

    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

}