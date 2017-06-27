package me.rayzr522.punishme.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArrayUtils {
    /**
     * Joins the elements of an array with a given filler.
     *
     * @param array  The array to concatenate.
     * @param filler The filler to use.
     * @return The concatenated array.
     */
    public static String concat(Object[] array, String filler) {
        Objects.requireNonNull(array, "array cannot be null!");

        return concat(Arrays.asList(array), filler);
    }

    /**
     * Joins the elements of a list with a given filler.
     *
     * @param list   The list to concatenate.
     * @param filler The filler to use.
     * @return The concatenated list.
     */
    public static String concat(List<?> list, String filler) {
        Objects.requireNonNull(list, "list cannot be null!");
        Objects.requireNonNull(filler, "filler cannot be null!");

        return list.stream().map(Objects::toString).collect(Collectors.joining(filler));
    }
}
