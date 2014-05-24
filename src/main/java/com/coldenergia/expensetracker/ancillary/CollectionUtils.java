package com.coldenergia.expensetracker.ancillary;

import java.util.ArrayList;
import java.util.List;

/**
 * User: coldenergia
 * Date: 5/24/14
 * Time: 4:24 PM
 */
public class CollectionUtils {

    /**
     * @return a list containing elements from the iterable,
     * or a new empty list if iterable was {@code null} or had no elements.
     * */
    public static <T> List<T> listFromIterable(Iterable<T> iterable) {
        boolean hasNoElements = (iterable == null || !iterable.iterator().hasNext());
        if (hasNoElements) {
            return new ArrayList<>();
        }

        List<T> list = new ArrayList<>();
        for (T t : iterable) {
            list.add(t);
        }
        return list;
    }

}
