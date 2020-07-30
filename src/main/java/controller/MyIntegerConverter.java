package controller;

import javafx.util.converter.IntegerStringConverter;

public class MyIntegerConverter extends IntegerStringConverter {
    @Override
    public Integer fromString(String s) {
        return s.isEmpty() || !isNumber(s) ? null : super.fromString(s);
    }

    public boolean isNumber(String value){
        int size = value.length();
        for (int i = 0; i < size; i++) {
            if (!Character.isDigit(value.charAt(i))){
                return false;
            }
        }
        return size > 0;
    }
}
