package ru.stqa.pft.sandbox;

import org.testng.annotations.Test;

public class TestSquare {

    @Test
    public void testArea() {
        Square square = new Square(5);
        assert square.area() == 25;
    }
}
