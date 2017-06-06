package co.neatapps.allchristsongs.android.model;

public class Digest {

    private DigestType digest;
    private int number;

    public Digest(DigestType digest, int number) {
        this.digest = digest;
        this.number = number;
    }

    public DigestType getDigest() {
        return digest;
    }

    public int getNumber() {
        return number;
    }

    public enum DigestType {
        revivalSong,
        maykop;

        public static int length() {
            return DigestType.values().length;
        }
    }

}