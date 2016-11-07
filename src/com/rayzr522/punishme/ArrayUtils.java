
package com.rayzr522.punishme;

import java.util.List;

public class ArrayUtils {

    public static String concat(Object[] arr, String filler) {

        if (arr.length < 1) {
            return "";
        }

        filler = filler == null ? "" : filler;

        String output = arr[0].toString();

        for (int i = 1; i < arr.length; i++) {

            output += filler + arr[i].toString();

        }

        return output;

    }

    public static String concat(List<? extends Object> arr, String filler) {

        if (arr.size() < 1) {
            return "";
        }

        filler = filler == null ? "" : filler;

        String output = arr.get(0).toString();

        for (int i = 1; i < arr.size(); i++) {

            output += filler + arr.get(i).toString();

        }

        return output;

    }

}
