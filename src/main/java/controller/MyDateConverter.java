package controller;

import javafx.util.converter.DateStringConverter;

import java.util.Date;

public class MyDateConverter extends DateStringConverter {
    public MyDateConverter(String s) {
        super(s);
    }

    @Override
    public Date fromString(String s) {
        try {
            return super.fromString(s);
        }catch (RuntimeException e){
            return null;
        }
    }
}
