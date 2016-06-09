package uk.ac.london.co3326;

import java.util.Random;

public class User {

    private Rsa rsa;
    private Integer nonce;
    private transient String name;
    
    public User(String name) {
        this.name = name;
    }
        
    public int getN() {
    	if (rsa == null) {
    		rsa = new Rsa();
    	}
        return rsa.getN();
    }

    public int getE() {
    	if (rsa == null) {
    		rsa = new Rsa();
    	}
        return rsa.getE();
    }

    public int getD() {
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
        int[] encoded = Util.toByteArray(message);
        return new Message(message, encoded, Rsa.crypt(encoded, getD(), getN()));
    }

    public Message encrypt(String message) {
        int[] encoded = Util.toByteArray(message);
        return new Message(message, encoded, Rsa.crypt(encoded, getE(), getN()));
    }
    
    public int[] send(User user, String message) {
        int[] input = Util.toByteArray(message);
        // encrypt with the smaller first
        if (getN() < user.getN()) {
            return Rsa.crypt(Rsa.crypt(input, getD(), getN()), user.getE(), user.getN());                                       
        } else {
            return Rsa.crypt(Rsa.crypt(input, user.getE(), user.getN()), getD(), getN());
        }
    }
    
    public String receive(User user, int[] cipher) {
        // decrypt with the larger first
        int[] message = (getN() > user.getN()) ? 
                Rsa.crypt(Rsa.crypt(cipher, getD(), getN()), user.getE(), user.getN()) :
                Rsa.crypt(Rsa.crypt(cipher, user.getE(), user.getN()), getD(), getN());
    	return Util.toString(message) + " :: received";
    }
   
    public String intercept(User user, int[] cipher) {
        // decrypt with the larger first
        int[] message = (getN() > user.getN()) ? 
                Rsa.crypt(Rsa.crypt(cipher, getD(), getN()), user.getE(), user.getN()) :
                Rsa.crypt(Rsa.crypt(cipher, user.getE(), user.getN()), getD(), getN());
        return Util.toString(message) + " :: intercepted";
    }
    
    public Rsa getRsa() {
        return this.rsa;
    }
    
}
