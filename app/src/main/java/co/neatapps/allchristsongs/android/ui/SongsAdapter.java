package co.neatapps.allchristsongs.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.neatapps.allchristsongs.allchristiansongs.R;
import co.neatapps.allchristsongs.android.model.Song;

public class SongsAdapter extends BaseAdapter {

    private Activity context;
    private List<Song> songs;

    public SongsAdapter(Activity context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_songs_list, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.header = (TextView) convertView.findViewById(R.id.item_songs_list_header);
            holder.body = (TextView) convertView.findViewById(R.id.item_songs_list_body);

            convertView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        final Song song = songs.get(position);
        holder.header.setText(song.getDigests().get(0).getNumber() + " " + song.getHeader());
//        holder.body.setText(songs.get(position).getBody());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongActivity.class);
                intent.putExtra(SongActivity.SONG, song.getBody());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView header;
        TextView body;
    }

}
