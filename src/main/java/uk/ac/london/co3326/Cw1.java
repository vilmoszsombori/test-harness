package uk.ac.london.co3326;

import java.util.List;

public class Cw1 implements Coursework {

    private User alice;
    private User bob;
    private User charlie;
    private List<Message> communication;
    
    public Cw1() {}
    
    public User getAlice() {
    	if (alice == null)
    		alice = new User("alice");
    	return alice;
    }

    public User getBob() {
        if (bob == null)
            bob = new User("bob");
        return bob;
    }
    
    public User getCharlie() {
    	if (charlie == null)
    	    charlie = new User("charlie");
    	return charlie;
    }

    @Override
    public void demonstrate() {
        if (communication == null || communication.isEmpty()) {
            throw new RuntimeException("No message to send");
        }
        // message from Alice to Bob as intercepted by Charlie
        String message = communication.remove(0).getText();
        communication.add(new Message(message, Util.toByteArray(message)));
        int[] cipher = getAlice().send(getCharlie(), message);
        communication.add(new Message(cipher));
        String intercepted = getCharlie().intercept(getAlice(), cipher);
        communication.add(new Message(intercepted, Util.toByteArray(intercepted)));
        cipher = getCharlie().send(getBob(), intercepted);
        communication.add(new Message(cipher));
        String received = getBob().receive(getCharlie(), cipher);
        communication.add(new Message(received, Util.toByteArray(received)));
    }

}
