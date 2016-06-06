package uk.ac.london.co3326;

import java.util.Arrays;
import java.util.List;

public class Rsa {

    public static final int MAX = 1024;
    private static List<Integer> primes = Util.sieve(MAX);
    
    private int p = 0;
    private int q = 0;
    private int e = 0;

    private int n = 0;
    private int r = 0;
    private int d = 0;

    public Rsa() {
    }

    public int getP() {
    	if (p == 0) {
            int i = Util.random(primes.size() / 2);
            p = primes.get(i);    		
    	}
        return p;
    }

    public void setP(int p) {
        if (!Util.isPrime(p)) {
            throw new RuntimeException(String.format("p = %d is not a prime", p));
        }
        this.p = p;
    }

    public int getQ() {
        if (q == 0) {
            int j = primes.size() / 2 + Util.random(primes.size() / 2);
            q = primes.get(j);
        }
        return q;
    }

    public void setQ(int q) {
        if (!Util.isPrime(q)) {
            throw new RuntimeException(String.format("q = %d is not a prime", q));
        }
        this.q = q;
    }

    public int getE() {
    	if (e == 0) {
            while (Util.gcd(e, getR()) != 1)
                e = 2 + Util.random(getR() - 1);    		
    	}
        return e;
    }

    public void setE(int e) {
        if (Util.gcd(e, getR()) != 1) {
            throw new RuntimeException(String.format("e = %d and r = %d are not co-primes", e, getR()));
        }    	
        this.e = e;
    }

    public int getN() {
        if (n == 0) {
            n = getP() * getQ();
        }
        return n;
    }

    public int getR() {
        if (r == 0) {
            r = (getP() - 1) * (getQ() - 1);
        }
        return r;
    }
    
    public int getD() {
    	if (d == 0) {
    		d = Util.modInverse(getE(), getR());
    	}
    	return d;
    }
    
    public static int[] crypt(int[] message, int exp, int mod) {
    	return Arrays.stream(message).map(i -> Util.modExp(i, exp, mod)).toArray();
    }
}
