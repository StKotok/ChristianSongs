package co.neatapps.allchristsongs.android.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Song {
    private Set<Digest> digests = new TreeSet<>(new DigestsComparator());
    private String header;
    private String tone;
    private String body;
    private String moreInfo;

    public Song(Digest digest, String header, String body, String tone, String moreInfo) {
        this.digests.add(digest);
        this.header = header;
        this.body = body;
        this.tone = tone;
        this.moreInfo = moreInfo;
    }

    public List<Digest> getDigests() {
        return new ArrayList<>(digests);
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String getTone() {
        return tone;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    private class DigestsComparator implements Comparator<Digest> {
        @Override
        public int compare(Digest o1, Digest o2) {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null) {
                return -1;
            } else if (o2 == null) {
                return 1;
            } else if (o1.getNumber() > o2.getNumber()) {
                return 1;
            } else if (o1.getNumber() < o2.getNumber()) {
                return 0;
            } else {
                return o1.getDigest().compareTo(o2.getDigest());
            }
        }
    }

}
