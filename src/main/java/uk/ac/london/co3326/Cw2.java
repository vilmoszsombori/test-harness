package uk.ac.london.co3326;

import java.util.ArrayList;
import java.util.List;

public class Cw2 {

    private User A;
    private User B;
    private User S;
    private List<Message> communication = new ArrayList<>();
    
    private static final String PHRASE_1 = "Dear %s, This is %s and I would like to get %s-s public key. Yours sincerely, %s.";
    private static final String PHRASE_2 = "Dear %s, Here is %s-s public key [%d,%d] signed by me. Yours sincerely, %s.";
    private static final String PHRASE_3 = "Dear %s, This is %s and I have sent you a nonce [%d] only you can read. Yours sincerely, %s.";
    private static final String PHRASE_4 = "Dear %s, Here is my nonce [%d] and yours [%d], proving I decrypted it. Yours sincerely, %s.";
    private static final String PHRASE_5 = "Dear %s, Here is your nonce [%d] proving I decrypted it. Yours sincerely, %s.";
    
    public Cw2() {}
    
    public User getA() {
        if (A == null)
            A = new User("A");
        A.setName("A");
        return A;
    }

    public User getB() {
        if (B == null)
            B = new User("B");
        B.setName("B");
        return B;
    }
    
    public User getS() {
        if (S == null)
            S = new User("S");
        S.setName("S");
        return S;
    }
        
    public void demonstrate() {
        // protocol of authentication between Alice and Bob using a trusted Server
        
        // generate A and B's keys
        getA().sign("bla");
        getB().sign("bla");
        
        // 1) Dear S, This is A and I would like to get B’s public key. Yours sincerely, A.
        String phrase = String.format(PHRASE_1, getS().getName(), getA().getName(), getB().getName(), getA().getName());
        communication.add(new Message(phrase, Util.toByteArray(phrase)));
        
        // 2) Dear A, Here is B’s public key signed by me. Yours sincerely, S.
        phrase = String.format(PHRASE_2, getA().getName(), getB().getName(), getB().getE(), getB().getN(), getS().getName());
        communication.add(getS().sign(phrase));
        
        // 3) Dear B, This is A and I have sent you a nonce only you can read. Yours sincerely, A.
        phrase = String.format(PHRASE_3, getB().getName(), getA().getName(), getA().getNonce(), getA().getName());
        communication.add(getB().encrypt(phrase));
        
        // 4) Dear S, This is B and I would like to get A’s public key. Yours sincerely, B.
        phrase = String.format(PHRASE_1, getS().getName(), getB().getName(), getA().getName(), getB().getName());
        communication.add(new Message(phrase, Util.toByteArray(phrase)));
        
        // 5) Dear B, Here is A’s public key signed by me. Yours sincerely, S.
        phrase = String.format(PHRASE_2, getB().getName(), getA().getName(), getA().getE(), getA().getN(), getS().getName());
        communication.add(getS().sign(phrase));
        
        // 6) Dear A, Here is my nonce and yours, proving I decrypted it. Yours sincerely, B.
        phrase = String.format(PHRASE_4, getA().getName(), getB().getNonce(), getA().getNonce(), getB().getName());
        communication.add(getA().encrypt(phrase));

        // 7) Dear B, Here is your nonce proving I decrypted it. Yours sincerely, A.
        phrase = String.format(PHRASE_5, getB().getName(), getB().getNonce(), getA().getName());
        communication.add(getB().encrypt(phrase));        
    }

}

