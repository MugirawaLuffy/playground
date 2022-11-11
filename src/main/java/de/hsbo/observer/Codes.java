package de.hsbo.observer;

import java.util.Dictionary;
import java.util.Hashtable;

public class Codes {
    private static Codes instance = null;
    int minimumCode = 0;
    int maximumCode = 2000;

    private Hashtable<String, Integer> takenCodes;
    private Codes() {
        takenCodes = new Hashtable<>();
    }

    public void addCode(String name, int code) {
        takenCodes.put(name, code);
    }

    public int getByName(String name) {
        if(name.split("#").length == 1)
            return takenCodes.get(name+"#0");
        return takenCodes.get(name);
    }

    public int getFreeCode() {
        for (int i = minimumCode; i <= maximumCode; i++) {
            if(!takenCodes.contains(i))
                return 1;
        }
        return -1;
    }

    public static Codes getInstance() {
        if (instance == null)
            instance = new Codes();

        return instance;
    }
}
