package ru.stqa.pft.addressbook;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSquare {

    @Test
    public void testArea() {
        Square square = new Square(5);
        Assert.assertEquals(square.area(), 25);
    }
}
