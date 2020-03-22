package ru.stqa.pft.addressbook.sandboxtests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.sandbox.Primes;

public class PrimesTests {

    @Test
    public void testPrime() {
        Assert.assertTrue(Primes.isPrime(Integer.MAX_VALUE));
    }

    @Test(enabled = false)
    public void testPrimeLong() {
        long n = Integer.MAX_VALUE;
        Assert.assertTrue(Primes.isPrime(n));
    }

    @Test
    public void testNotPrime() {
        Assert.assertFalse(Primes.isPrime(Integer.MAX_VALUE - 2));
    }
}
