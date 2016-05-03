package co.neatapps.allchristsongs.android.util;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stkotok on 02.05.2016.
 */
public class Utils {
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
}
