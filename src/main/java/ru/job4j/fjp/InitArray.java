package ru.job4j.fjp;

public class InitArray {
    public static Model[] get(int capacity) {
        Model[] array = new Model[capacity];
        for (int i = 0; i < capacity; i++) {
            array[i] = new Model(i);
        }
        return array;
    }
}
