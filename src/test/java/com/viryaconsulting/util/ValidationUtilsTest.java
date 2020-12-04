package com.viryaconsulting.util;

import com.viryaconsulting.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ValidationUtilsTest extends BaseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_isNumber_True_For_One() {
        Assert.assertTrue(ValidationUtils.isNumber("1"));
    }

    @Test
    public void test_isNumber_True_For_NegativeOne() {
        Assert.assertTrue(ValidationUtils.isNumber("-1"));
    }

    @Test
    public void test_isNumber_False_For_Letter() {
        Assert.assertFalse(ValidationUtils.isNumber("A"));
    }

    @Test
    public void test_isNumber_False_For_NaN() {
        Assert.assertFalse(ValidationUtils.isNumber("NaN"));
    }

    @Test
    public void test_isNumber_False_For_Infinity() {
        Assert.assertFalse(ValidationUtils.isNumber("Infinity"));
    }

    @Test
    public void test_isNumber_False_For_For_Exponential_e() {
        Assert.assertFalse(ValidationUtils.isNumber("e"));
    }

    @Test
    public void test_isNumber_False_For_For_Exponential_E() {
        Assert.assertFalse(ValidationUtils.isNumber("e"));
    }

    //check this
    @Test
    public void test_isNumber_False_For_Hexa_X() {
        Assert.assertFalse(ValidationUtils.isNumber("X"));
    }

    @Test
    public void test_isBuyOrSale_True_S() {
        Assert.assertTrue(ValidationUtils.isBuyOrSale("S"));
    }

    @Test
    public void test_isBuyOrSale_True_B() {
        Assert.assertTrue(ValidationUtils.isBuyOrSale("B"));
    }

    @Test
    public void test_isBuyOrSale_False_For_Non_S_or_B() {
        Assert.assertFalse(ValidationUtils.isBuyOrSale("1"));
    }

    @Test
    public void test_isNonEmptyString_True_For_String_A() {
        Assert.assertTrue(ValidationUtils.isNonEmptyString("A"));
    }

    @Test
    public void test_isNonEmptyString_True_For_String_a() {
        Assert.assertTrue(ValidationUtils.isNonEmptyString("a"));
    }

    @Test
    public void test_isNonEmptyString_False_For_String_empty() {
        Assert.assertFalse(ValidationUtils.isNonEmptyString(""));
    }

    @Test
    public void test_isNonEmptyString_False_For_String_null() {
        Assert.assertFalse(ValidationUtils.isNonEmptyString(null));
    }

    @Test
    public void test_isPositiveNumber_True_For_One() {
        Assert.assertTrue(ValidationUtils.isPositiveNumber("1"));
    }

    @Test
    public void test_isNonEmptyString_False_For_NegativeOne() {
        Assert.assertFalse(ValidationUtils.isPositiveNumber("-1"));
    }

    @Test
    public void test_isNonEmptyString_False_For_Letter() {
        Assert.assertFalse(ValidationUtils.isPositiveNumber("A"));
    }

    @Test
    public void test_Exception_Create_Instance() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        expectedException.expect(InvocationTargetException.class);

        Constructor<ValidationUtils> constructor = ValidationUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ValidationUtils validationUtils = constructor.newInstance();

    }

    @Before
    public void init() throws IOException {
        if (validationUtilsMockedStatic != null) {
            validationUtilsMockedStatic.close();
            validationUtilsMockedStatic = null;
        }
    }
}
