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

}