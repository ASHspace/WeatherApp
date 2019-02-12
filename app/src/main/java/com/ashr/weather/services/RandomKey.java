package com.ashr.weather.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomKey {

    static final List<String> keys = new ArrayList<String>() {{
        add("1ae7dea85e939c125b0c12e8ad6904e3");
        add("09d9b6c1edf794d0b2ad5eb91085f55b");
        add("6420d01b85b19e1f1fd5eccb14567edc");
    }};

    public String myrandomKey(){
        return keys.get(new Random().nextInt(keys.size()));
    }

}
