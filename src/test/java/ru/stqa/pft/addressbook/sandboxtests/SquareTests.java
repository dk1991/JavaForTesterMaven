package ru.stqa.pft.addressbook.sandboxtests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.sandbox.Square;

import static org.testng.Assert.assertEquals;

public class SquareTests {

    @Test
    public void testArea() {
        Square square = new Square(5.0);
        assertEquals(square.area(), 25.0);
    }
}
