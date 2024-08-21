package com.agrusi.backendapi.unit.util;

import java.lang.reflect.Field;

public class ReflectionTestUtils {

    /*
     * With the "setField" method, we can create (mimic) fields in our
     * tests that we don't have access to in our code, because we wanted
     * to keep them private or don't include them at all.
     *
     * Fields like this include the setter methods for identifier
     * fields because they never change.
    */

    public void setField(
            Object target, String fieldName, Object value
    ) throws NoSuchFieldException, IllegalAccessException {

        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
