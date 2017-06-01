package co.neatapps.allchristsongs.android.model;

import java.util.Map;
import java.util.TreeMap;

public class Song {
    private Map<Digest.DigestType, Integer> numbers = new TreeMap<>();
    private String header;
    private String body;

    public Song(Map<Digest.DigestType, Integer> numbers, String header, String body) {
        this.numbers = numbers;
        this.header = header;
        this.body = body;
    }

    public Map<Digest.DigestType, Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(Map<Digest.DigestType, Integer> numbers) {
        this.numbers = numbers;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
