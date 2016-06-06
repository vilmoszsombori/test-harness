package uk.ac.london.co3326;

import java.util.Arrays;

public class Message {

    private String text;
    private int[] encoded;
    private int[] encrypted;

    public Message() {}

    public Message(String text, int[] encoded, int[] encrypted) {
        super();
        this.text = text;
        this.encoded = encoded;
        this.encrypted = encrypted;
    }

    public Message(String text, int[] encoded) {
        super();
        this.text = text;
        this.encoded = encoded;
    }
    
    public Message(String text) {
        super();
        this.text = text;
    }

    public Message(int[] encoded) {
        super();
        this.encoded = encoded;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int[] getEncoded() {
        return encoded;
    }
    
    public void setEncoded(int[] encoded) {
        this.encoded = encoded;
    }
    
    public int[] getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(int[] encypted) {
        this.encrypted = encypted;
    }

    @Override
    public String toString() {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("Message [");
        if (text != null) {
            builder.append("text=");
            builder.append(text);
            builder.append(", ");
        }
        if (encoded != null) {
            builder.append("encoded=");
            builder.append(
                    Arrays.toString(Arrays.copyOf(encoded, Math.min(encoded.length, maxLen))));
            builder.append(", ");
        }
        if (encrypted != null) {
            builder.append("encrypted=");
            builder.append(
                    Arrays.toString(Arrays.copyOf(encrypted, Math.min(encrypted.length, maxLen))));
        }
        builder.append("]");
        return builder.toString();
    }
    
}
