package ru.job4j.cache;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddNewThenAdded() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        assertTrue(cache.add(model));
    }

    @Test
    public void whenAddOldThenNotAdded() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        assertFalse(cache.add(new Base(1, 0)));
    }

    @Test
    public void whenUpdateWithSameVerThenUpdatedWithNewVersion() {
        Cache cache = new Cache();
        ArrayList<String> result = new ArrayList<>();
        Base model = new Base(1, 0);
        cache.add(model);
        model.setName("Stas");
        cache.update(model);
        result.add(String.valueOf(cache.get(1).getId()));
        result.add(String.valueOf(cache.get(1).getVersion()));
        result.add(cache.get(1).getName());
        assertEquals(List.of("1", "1", "Stas"), result);
    }
    @Test
    public void whenSameVersionAndIdThenTrue() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        Base model2 = new Base(1, 0);
        cache.add(model1);
        assertTrue(cache.update(model2));
    }

    @Test
    public void whenSameVersionAndDifIdThenFalse() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        Base model2 = new Base(2, 0);
        cache.add(model1);
        assertFalse(cache.update(model2));
    }
    @Test
    (expected = OptimisticException.class)
    public void whenDifVersionAndIdThenThrow() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 0);
        Base model2 = new Base(1, 1);
        cache.add(model1);
        cache.update(model2);
    }

    @Test
    public void whenDeleteThenNoSuchElement() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        cache.delete(model);
        assertNull(cache.get(1));
    }
}