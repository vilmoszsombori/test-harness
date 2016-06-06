package uk.ac.london.co3326;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Util {
	private static Random random = null;

	public static boolean isPrime(int n) {
		// initially assume all integers are prime
		boolean[] isPrime = new boolean[n + 1];
		for (int i = 2; i <= n; i++) {
			isPrime[i] = true;
		}

		// mark non-primes <= n using Sieve of Eratosthenes
		for (int i = 2; i * i <= n; i++) {
			// if i is prime, then mark multiples of i as nonprime
			// suffices to consider multiples i, i+1, ..., N/i
			if (isPrime[i]) {
				for (int j = i; i * j <= n; j++) {
					int k = i * j;
					if (k == n)
						return false;
					isPrime[k] = false;
				}
			}
		}

		return isPrime[n];
	}

	public static List<Integer> sieve(int max) {
		// initially assume all integers are prime
		boolean[] isPrime = new boolean[max + 1];
		for (int i = 2; i <= max; i++) {
			isPrime[i] = true;
		}

		// mark non-primes <= N using Sieve of Eratosthenes
		for (int i = 2; i * i <= max; i++) {

			// if i is prime, then mark multiples of i as nonprime
			// suffices to consider multiples i, i+1, ..., N/i
			if (isPrime[i]) {
				for (int j = i; i * j <= max; j++) {
					isPrime[i * j] = false;
				}
			}
		}

		List<Integer> primes = new ArrayList<>();
		for (int i = 2; i <= max; i++) {
			if (isPrime[i])
				primes.add(i);
		}

		return primes;
	}

	public static int ipow(int base, int exp) {
		int result = 1;
		while (exp != 0) {
			if ((exp & 1) != 0)
				result *= base;
			exp >>= 1;
			base *= base;
		}

		return result;
	}

	public static int ipow2(int base, int exp) {
		if (exp == 0) {
			return 1;
		} else {
			return ((exp & 1) != 0) ? ipow2(base * base, exp >> 1) * base : ipow2(base * base, exp >> 1);
		}
	}

	public static int gcd(int a, int b) {
		return b == 0 ? Math.abs(a) : gcd(b, a % b);
	}

	public static int modExp(int base, int exp, int mod) {
		long x = 1;
		long y = base;
		while (exp > 0) {
			if (exp % 2 == 1) {
				x = (x * y) % mod;
			}
			y = (y * y) % mod;
			exp /= 2;
		}
		return (int) x % mod;
	}

	public static int modInverse(int a, int n) {
	    /*
	    BigInteger ba = new BigInteger(Integer.toString(a));
	    BigInteger bn = new BigInteger(Integer.toString(n));
	    
	    BigInteger mi = ba.modInverse(bn);
	    
	    return mi.intValue();
	    */
		int i = n, v = 0, d = 1;
		while (a > 0) {
			int t = i / a, x = a;
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

    public static int[] toByteArray(String message) {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        return IntStream.generate(buffer::get).limit(buffer.remaining()).toArray();
    }

    public static String toString(int[] message) {
        return Arrays.stream(message)
                .mapToObj(i -> (char) i)
                .reduce(new StringBuilder(), (sb, c) -> sb.append(c), StringBuilder::append)
                .toString();
    }
	
	public static int random(int n) {
		if (random == null)
			random = new Random();
		return random.nextInt(n);		
	}
}
