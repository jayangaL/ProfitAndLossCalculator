package com.viryaconsulting.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstantsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_Exception_Create_Instance() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        expectedException.expect(InvocationTargetException.class);

        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Constants constants = constructor.newInstance();

    }
}
