package uk.ac.london.co3326;

import java.util.Random;

public class User {

    private Rsa rsa;
    private Integer nonce;
    private transient String name;
    
    public User(String name) {
        this.name = name;
    }
        
    public long getN() {
    	if (rsa == null) {
    		rsa = new Rsa();
    	}
        return rsa.getN();
    }

    public long getE() {
    	if (rsa == null) {
    		rsa = new Rsa();
    	}
        return rsa.getE();
    }

    public long getUncomputedD() {
        return rsa.getUncomputedD();        
    }
    
    public long getD() {
    	if (rsa == null) {
    		rsa = new Rsa();
    	}
        return rsa.getD();
    }
    
    public Integer getNonce() {
        if (nonce == null) {
            Random random = new Random();
            nonce = random.nextInt(Integer.MAX_VALUE);
        }
        return nonce;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Message sign(String message) {
        long[] encoded = Util.toByteArray(message);
        return new Message(message, encoded, Rsa.crypt(encoded, getD(), getN()));
    }

    public Message encrypt(String message) {
        long[] encoded = Util.toByteArray(message);
        return new Message(message, encoded, Rsa.crypt(encoded, getE(), getN()));
    }
    
    public long[] send(User user, String message) {
        long[] input = Util.toByteArray(message);
        // encrypt with the smaller first
        if (getN() < user.getN()) {
            return Rsa.crypt(Rsa.crypt(input, getD(), getN()), user.getE(), user.getN());                                       
        } else {
            return Rsa.crypt(Rsa.crypt(input, user.getE(), user.getN()), getD(), getN());
        }
    }
    
    public String receive(User user, long[] cipher) {
        // decrypt with the larger first
        long[] message = (getN() > user.getN()) ? 
                Rsa.crypt(Rsa.crypt(cipher, getD(), getN()), user.getE(), user.getN()) :
                Rsa.crypt(Rsa.crypt(cipher, user.getE(), user.getN()), getD(), getN());
    	return Util.toString(message) + " :: received";
    }
   
    public String intercept(User user, long[] cipher) {
        // decrypt with the larger first
        long[] message = (getN() > user.getN()) ? 
                Rsa.crypt(Rsa.crypt(cipher, getD(), getN()), user.getE(), user.getN()) :
                Rsa.crypt(Rsa.crypt(cipher, user.getE(), user.getN()), getD(), getN());
        return Util.toString(message) + " :: longercepted";
    }
    
    public Rsa getRsa() {
        return this.rsa;
    }
    
}
