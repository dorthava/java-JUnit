package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5})
    void isPrimeForPrimes(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8})
    void isPrimeForNotPrimes(int number) {
        NumberWorker numberWorker = new NumberWorker();
        assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void isPrimeForIncorrectNumbers(int value) {
        NumberWorker numberWorker = new NumberWorker();
        assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(value));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void digitsSum(int number, int numberAns) {
        NumberWorker numberWorker = new NumberWorker();
        assertEquals(numberWorker.digitsSum(number), numberAns);
    }
}
