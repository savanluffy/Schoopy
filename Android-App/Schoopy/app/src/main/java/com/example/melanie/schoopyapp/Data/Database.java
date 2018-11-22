package com.example.melanie.schoopyapp.Data;

import android.os.Bundle;

public class Database {
    private static Database db=null;

    public Database(){

    }

    public static Database newInstance() {

        if(db==null){
            db=new Database();
        }
        return null;
    }

    public void setURL(String s) {
    }
}
