package co.neatapps.allchristsongs.android.util;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.neatapps.allchristsongs.allchristiansongs.R;
import co.neatapps.allchristsongs.android.model.Digest;
import co.neatapps.allchristsongs.android.model.Song;

import static co.neatapps.allchristsongs.android.util.SongsParser.Marker.start;

public class SongsParser {
    private static final String NUMBER_PREFIX = "#number_";
    private static final String TONE_PREFIX = "#tone_";
    private static final String TEXT_PREFIX = "#text___";

    public static List<Song> parse(Activity activity) {
        ArrayList<Song> songs = new ArrayList<>();

        String rawTextFile = Files.readRawTextFile(activity, R.raw.songs);
        String[] rawSongs = rawTextFile.split("\n\n\n");
        int i = 0;
        for (String rawSong : rawSongs) {

            String[] split = rawSong.split("\n");

            Marker marker = start;
            StringBuilder moreInfo = new StringBuilder();
            int songNumber = 0;
            String tone = "";
            StringBuilder text = new StringBuilder();

            int nBody = 0;
            for (String s : split) {
                switch (marker) {
                    case start:
                    case addInfo:
                        if (s.startsWith(NUMBER_PREFIX)) {
                            String numb = s.substring(NUMBER_PREFIX.length(), s.length());
                            songNumber = Integer.valueOf(numb);
                            marker = Marker.tone;
                        } else {
                            marker = Marker.addInfo;
                            moreInfo.append(s).append("\n");
                        }
                        break;

                    case tone:
                        if (s.startsWith(TONE_PREFIX)) {
                            tone = s.substring(TONE_PREFIX.length(), s.length());
                            marker = Marker.body0;
                        } else if (s.startsWith(TEXT_PREFIX)) {
                            marker = Marker.body;
                        }
                        break;

                    case body0:
                        if (s.equals(TEXT_PREFIX)) {
                            marker = Marker.body;
                        } else {
                            Log.e("==>", songNumber + ": mising " + TEXT_PREFIX);
                            return songs;
                        }
                        break;

                    case body:
                        nBody++;
                        text.append(s).append("\n");
                        break;
                }
            }

            if (nBody < 1) {
                Log.d("==>", "#=" + songNumber + "  body=" + nBody);
            }

            Digest digest = new Digest(Digest.DigestType.revivalSong, songNumber);
            Song song = new Song(digest, text.toString().split("\n")[0], text.toString(), tone, moreInfo.toString());
            if (song.getBody().trim().length() > 10) {
                songs.add(song);
            } else {
                Log.e("==>", "#=" + songNumber + " skip");
            }

            if (rawSong.contains("*dupl*")) {
                Log.d("==>", "#=" + songNumber + " *dupl*");
                i--;
            }
            i++;

            if (i != songNumber) {
                Log.d("==>", "i=" + i + "  #=" + songNumber);
            }
        }

        Log.d("==>", "songs: " + songs.size());
        return songs;
    }

    enum Marker {
        start,
        addInfo,
        number,
        tone,
        body0,
        body
    }

}
