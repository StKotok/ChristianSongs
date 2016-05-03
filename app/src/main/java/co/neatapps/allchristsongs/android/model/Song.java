package co.neatapps.allchristsongs.android.model;

import java.util.Map;
import java.util.TreeMap;

public class Song {
    private Map<Digest.DigestType, Integer> number = new TreeMap<>();
    private String header;
    private String body;

}
