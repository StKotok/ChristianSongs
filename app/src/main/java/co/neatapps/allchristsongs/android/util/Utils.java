package co.neatapps.allchristsongs.android.util;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.format.Time;
import android.text.style.StyleSpan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {

    public static final Pattern PATTERN_NUMBER = Pattern.compile("^\\d+$");

    public static Spannable getItalicText(String text) {
        Spannable italic = new SpannableString(text);
        italic.setSpan(new StyleSpan(Typeface.ITALIC), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return italic;
    }

    public static Spannable getBoldText(String text) {
        Spannable italic = new SpannableString(text);
        italic.setSpan(new StyleSpan(Typeface.BOLD), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return italic;
    }

    @NonNull
    public static SpannableStringBuilder formatSongText(String songText) {
        SpannableStringBuilder ssb = new SpannableStringBuilder("");
        final List<String> textList = new ArrayList<>(Arrays.asList(songText.split("(<[ib]>)|(>)")));
        for (String s : textList) {
            SongsService.Formatting formatting = SongsService.Formatting.Default;
            if (s.endsWith("</i")) {
                formatting = SongsService.Formatting.Italic;
            }
            if (s.endsWith("</b")) {
                formatting = SongsService.Formatting.Bold;
            }
            switch (formatting) {
                case Italic:
                    s = s.substring(0, s.length() - 3);
                    ssb.append(getItalicText(s));
                    break;
                case Bold:
                    s = s.substring(0, (s.length() - 3));
                    ssb.append(getBoldText(s));
                    break;
                case Default:
                    if (s.length() != 0) {
                        ssb.append(s);
                    }
                    break;
                default:
                    // Something went wrong
            }
        }
        return ssb;
    }

    public static boolean isNaturalNumber(CharSequence input) {
        return input != null && PATTERN_NUMBER.matcher(input).matches();
    }

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static void saveBoolean(boolean b, String key, SharedPreferences sharedPref) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public static void hideActionBar(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.hide();
        }
    }

}
