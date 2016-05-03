package co.neatapps.allchristsongs.android.util;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class SongsAdapter extends ArrayAdapter {
    public SongsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public SongsAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public SongsAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public SongsAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public SongsAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public SongsAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
