package uk.ac.london.co3326;

import java.util.Arrays;
import java.util.List;

public class Rsa {

    public static final int MAX = 2048;
    private static List<Long> primes = Util.sieve(MAX);
    
    private long p = 0;
    private long q = 0;
    private long e = 0;

    private long n = 0;
    private long r = 0;
    private long d = 0;

    public Rsa() {
    }

    public long getP() {
    	if (p == 0) {
            int i = (int)Util.random(primes.size() / 2);
            p = primes.get(i);    		
    	}
        return p;
    }

    public void setP(long p) {
        if (!Util.isPrime(p)) {
            throw new RuntimeException(String.format("p = %d is not a prime", p));
        }
        this.p = p;
    }

    public long getQ() {
        if (q == 0) {
            int j = (int)(primes.size() / 2 + Util.random(primes.size() / 2));
            q = primes.get(j);
        }
        return q;
    }

    public void setQ(long q) {
        if (!Util.isPrime(q)) {
            throw new RuntimeException(String.format("q = %d is not a prime", q));
        }
        this.q = q;
    }

    public long getUncomputedE() {
        return e;        
    }    
    
    public long getE() {
    	if (e == 0) {
            while (Util.gcd(e, getR()) != 1)
                e = 2 + Util.random(getR() - 1);    		
    	}
        return e;
    }

    public void setE(long e) {
        if (Util.gcd(e, getR()) != 1) {
            throw new RuntimeException(String.format("e = %d and r = %d are not co-primes", e, getR()));
        }    	
        this.e = e;
    }

    public long getN() {
        if (n == 0) {
            n = getP() * getQ();
        }
        return n;
    }

    public long getR() {
        if (r == 0) {
            r = (getP() - 1) * (getQ() - 1);
        }
        return r;
    }
    
    public long getUncomputedD() {
        return d;        
    }
        
    public long getD() {
    	if (d == 0) {
    		d = Util.modInverse(getE(), getR());
    	}
    	return d;
    }
    
    public static long[] crypt(long[] message, long exp, long mod) {
    	return Arrays.stream(message).map(i -> Util.modExp(i, exp, mod)).toArray();
    }
}
