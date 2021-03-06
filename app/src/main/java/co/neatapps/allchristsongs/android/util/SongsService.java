package co.neatapps.allchristsongs.android.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.neatapps.allchristsongs.android.model.Digest;
import co.neatapps.allchristsongs.android.model.Song;

public class SongsService {
    private static List<Song> songs;

    public static List<Song> getSongs() {

        // STUB start
        songs = new ArrayList<>();
        HashMap<Digest.DigestType, Integer> numbers1 = new HashMap<>();
        numbers1.put(Digest.DigestType.maykop, 1);
        numbers1.put(Digest.DigestType.revivalSong, 1);
        HashMap<Digest.DigestType, Integer> numbers2 = new HashMap<>();
        numbers2.put(Digest.DigestType.maykop, 312);
        numbers2.put(Digest.DigestType.revivalSong, 2);
//        songs.add(new Song(numbers1, "Aнгелы в небе", songTextSTUB()));
//        songs.add(new Song(numbers2, "Жизнь посвящаю Тебе", songTextSTUB312()));
        // STUB end

        return songs;
    }

    public static String songTextSTUB() {
        String text = "<b>#</b>" +
                "Ангелы в небе Господа славят,\n" +
                "Славу достойную Богу поют, Богу поют:\n" +
                "Вечная слава, вечная слава,\n" +
                "Вечная слава, слава Христу! Слава Христу!\n" +
                "\n" +
                "Хочется с ангельским пением слиться\n" +
                "В хоре искупленных, спасенных Господом.\n" +
                "И сердце Господу, Богу единому,\n" +
                "Христу Спасителю славу поет, славу поет.\n" +
                "\n" +
                "<i>Вечная слава, вечная слава,\n" +
                "Вечная слава, слава Христу! Слава Христу!\n" +
                "Вечная слава, вечная слава,\n" +
                "Вечная слава, слава Христу!</i>\n" +
                "\n" +
                "Здесь, на земле людей, Церковь томится,\n" +
                "К небу стремится, дух к Жениху влечёт.\n" +
                "О, гряди, Господи, видишь, невеста ждёт,\n" +
                "Тебе Единому славу поёт, славу поет.\n" +
                "\n" +
                "Здесь, на земле людей, Церковь томится,\n" +
                "К небу стремится, дух к Жениху влечёт.\n" +
                "О, гряди, Господи, видишь, невеста ждёт,\n" +
                "Тебе Единому славу поёт, славу поет.\n" +
                "\n" +
                "Чудный, прославленный, царственный Божий Сын,\n" +
                "Дивный Христос Господь в небе нас ждёт.\n" +
                "И сердце Господу, Богу Единому,\n" +
                "Христу Спасителю славу поёт, славу поет.";
        return text;
    }

    public static String songTextSTUB312() {
        String text = "<b>#</b>\n\n" +
                "<i>Жизнь посвящаю Тебе,</i>\n" +
                "И отдаюсь я в мольбе.\n" +
                "Ты управляй ею Сам,\n" +
                "И направляй к небесам.\n" +
                "Жизнь эта - не моя,\n" +
                "Так будет пусть Твоя,\n" +
                "Её Ты Сам мне дал,\n" +
                "Чтоб я Тебя прославлял.\n" +
                "\n" +
                "<i>Мир искушеньями полн,</i>\n" +
                "Хочет увлечь меня он,\n" +
                "Чтобы ему я служил\n" +
                "И по-греховному жил.\n" +
                "Но я Тебя молю:\n" +
                "Силу пошли Свою\n" +
                "И мне защиту дай,\n" +
                "В битве с грехом укрепляй.\n" +
                "\n" +
                "<i>Честно желаю я жить,</i>\n" +
                "Ближних хочу всех любить,\n" +
                "Зло и обиды прощать\n" +
                "И о Тебе возвещать.\n" +
                "О мой Господь и Бог,\n" +
                "Если б я в жизни смог\n" +
                "Твою любовь познать\n" +
                "И всем о ней рассказать!\n" +
                "\n" +
                "<i>Скоро Ты в славе придешь</i>\n" +
                "И Свою Церковь возьмешь.\n" +
                "Так приготовь и меня,\n" +
                "Чтобы достоин был я\n" +
                "Вечно с Тобой пребыть,\n" +
                "Злобу людей забыть\n" +
                "И в небе увидать\n" +
                "То, что Ты хочешь нам дать.";
        return text;
    }

    public enum Formatting {
        Default, Italic, Bold
    }
}
