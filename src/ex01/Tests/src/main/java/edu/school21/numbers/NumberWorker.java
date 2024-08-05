package edu.school21.numbers;

public class NumberWorker {
    public static void main(String[] args) {

    }

    public boolean isPrime(int number) {
        if(number <= 1) throw new IllegalNumberException();
        int divider = 2;
        while((divider * divider <= number) && (number % divider != 0)) {
            ++divider;
        }
        return divider * divider > number;
    }

    public int digitsSum(int number) {
        if(number < 0) number = -number;
        int result = 0;
        do {
            int digit = number % 10;
            result += digit;
        } while((number /= 10) != 0);
        return result;
    }
}
