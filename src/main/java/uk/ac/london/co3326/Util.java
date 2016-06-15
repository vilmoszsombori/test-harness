package uk.ac.london.co3326;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

public class Util {
	private static Random random = null;

	public static boolean isPrime(long p) {
    	BigInteger n = new BigInteger(Long.toString(p));
        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(n) < 0; i = i.add(BigInteger.ONE)) {
            if (n.mod(i).equals(BigInteger.ZERO)) {
                return false;
            }
        }
        return true;
		/*
		// initially assume all integers are prime
		List<Boolean> isPrime = new ArrayList<>((int)n+1);
		for (int i = 2; i <= n; i++) {
			isPrime.set(i, true);
		}

		// mark non-primes <= n using Sieve of Eratosthenes
		for (int i = 2; i * i <= n; i++) {
			// if i is prime, then mark multiples of i as nonprime
			// suffices to consider multiples i, i+1, ..., N/i
			if (isPrime.get(i)) {
				for (int j = i; i * j <= n; j++) {
					int k = i * j;
					if (k == n)
						return false;
					isPrime.set(k, false);
				}
			}
		}

		return isPrime.get((int)n);
		*/
	}

	public static List<Long> sieve(long max) {
		// initially assume all integers are prime
	    int N = (int) max;
		boolean[] isPrime = new boolean[N + 1];
		for (int i = 2; i <= max; i++) {
			isPrime[i] = true;
		}

		// mark non-primes <= N using Sieve of Eratosthenes
		for (int i = 2; i * i <= N; i++) {

			// if i is prime, then mark multiples of i as nonprime
			// suffices to consider multiples i, i+1, ..., N/i
			if (isPrime[i]) {
				for (int j = i; i * j <= N; j++) {
					isPrime[i * j] = false;
				}
			}
		}

		List<Long> primes = new ArrayList<>();
		for (long i = 2; i <= N; i++) {
			if (isPrime[(int)i])
				primes.add(i);
		}

		return primes;
	}

	public static long ipow(long base, long exp) {
		long result = 1;
		while (exp != 0) {
			if ((exp & 1) != 0)
				result *= base;
			exp >>= 1;
			base *= base;
		}

		return result;
	}

	public static long ipow2(long base, long exp) {
		if (exp == 0) {
			return 1;
		} else {
			return ((exp & 1) != 0) ? ipow2(base * base, exp >> 1) * base : ipow2(base * base, exp >> 1);
		}
	}

	public static long gcd(long a, long b) {
		return b == 0 ? Math.abs(a) : gcd(b, a % b);
	}

	public static long modExp(long base, long exp, long mod) {
		long x = 1;
		long y = base;
		while (exp > 0) {
			if (exp % 2 == 1) {
				x = (x * y) % mod;
			}
			y = (y * y) % mod;
			exp /= 2;
		}
		return x % mod;
	}

	public static long modInverse(long a, long n) {
	    /*
	    BigInteger ba = new BigInteger(Integer.toString(a));
	    BigInteger bn = new BigInteger(Integer.toString(n));
	    
	    BigInteger mi = ba.modInverse(bn);
	    
	    return mi.intValue();
	    */
		long i = n, v = 0, d = 1;
		while (a > 0) {
			long t = i / a, x = a;
			a = i % x;
			i = x;
			x = d;
			d = v - t * x;
			v = x;
		}
		v %= n;
		if (v < 0)
			v = (v + n) % n;
		return v;
	}

    public static long[] toByteArray(String message) {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        return LongStream.generate(buffer::get).limit(buffer.remaining()).toArray();
    }

    public static String toString(long[] message) {
        return Arrays.stream(message)
                .mapToObj(i -> (char) i)
                .reduce(new StringBuilder(), (sb, c) -> sb.append(c), StringBuilder::append)
                .toString();
    }
	
	public static long random(long n) {
		if (random == null)
			random = new Random();
		return (long)(random.nextDouble() * n);		
	}
}
