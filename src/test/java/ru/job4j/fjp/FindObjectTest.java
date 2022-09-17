package ru.job4j.fjp;

import org.junit.Test;

import static org.junit.Assert.*;

public class FindObjectTest {

    @Test
    public void whenObjectId50ThenIndexIs50() {
        Model[] array = InitArray.get(100);
        assertEquals(50, FindObject.find(array, new Model(50)));
    }

    @Test
    public void whenObjectIdOutOfBoundsThenMinusOne() {
        Model[] array = InitArray.get(100);
        assertEquals(-1, FindObject.find(array, new Model(110)));
    }

    @Test
    public void whenLessThan10ThenOk() {
        Model[] array = InitArray.get(9);
        assertEquals(3, FindObject.find(array, new Model(3)));
    }

    @Test
    public void whenLastElementThenOk() {
        Model[] array = InitArray.get(100);
        assertEquals(99, FindObject.find(array, new Model(99)));
    }

    @Test
    public void whenIntegerElementsThenOk() {
        Integer[] array = new Integer[]{1, 2, 3, 9, 5};
        assertEquals(3, FindObject.find(array, Integer.valueOf(9)));
    }

    @Test
    public void whenStringElementsThenOk() {
        String[] array = new String[] {"1", "2", "3", "9", "5"};
        assertEquals(3, FindObject.find(array, "9"));
    }

}