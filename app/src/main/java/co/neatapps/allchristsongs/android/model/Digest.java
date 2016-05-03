package co.neatapps.allchristsongs.android.model;

public class Digest {

    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public enum DigestType {
        revivalSong,
        maykop
    }

}