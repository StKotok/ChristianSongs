package co.neatapps.allchristsongs.android.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.neatapps.allchristsongs.allchristiansongs.R;
import co.neatapps.allchristsongs.android.model.Digest;

public class DigestsSelector {

    private static final String DIVIDER = ";";

    private final Activity activity;

    private final View vSearchInLayout;
    private final TextView vSearchInInfo;
    private final ViewGroup vSearchInContainer;
    private final View vSpaceOnTop;

    public DigestsSelector(Activity activity) {
        this.activity = activity;

        vSearchInLayout = activity.findViewById(R.id.search_in_layout);
        vSearchInInfo = (TextView) activity.findViewById(R.id.search_in_info);
        vSearchInContainer = (ViewGroup) activity.findViewById(R.id.search_in_container);
        vSpaceOnTop = activity.findViewById(R.id.space_on_autocomplete_top);

        initDigestLayoutVisibility();

        initDigestsLayout();
    }

    public List<String> getSelected() {
        ArrayList<String> selectedItems = new ArrayList<>();
        for (int i = 0; i < vSearchInContainer.getChildCount(); i++) {
            TextView textView = (TextView) vSearchInContainer.getChildAt(i);
            if (textView.isSelected()) {
                selectedItems.add(textView.getText().toString());
            }
        }
        return selectedItems;
    }

    private void initDigestLayoutVisibility() {
        View.OnClickListener layoutsClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDigestsLayoutVisible(vSearchInContainer.getVisibility() != View.VISIBLE);
            }
        };

        vSearchInLayout.setOnClickListener(layoutsClickListener);
        vSpaceOnTop.setOnClickListener(layoutsClickListener);

        setDigestsLayoutVisible(Prefs.DIGESTS_VISIBLE.getValue(activity));
    }

    private void setDigestsLayoutVisible(boolean digestsVisible) {
        vSearchInLayout.setSelected(digestsVisible);
        vSearchInContainer.setVisibility(digestsVisible ? View.VISIBLE : View.GONE);
        Prefs.DIGESTS_VISIBLE.setValue(activity, digestsVisible);
    }

    private void initDigestsLayout() {
        List<String> digests = new ArrayList<>();
        for (int i = 0; i < Digest.DigestType.length(); i++) {
            digests.add(Digest.DigestType.values()[i].toString());
        }

        View.OnClickListener digestsClickItemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                saveSelectedDigests();
                refreshSearchInInfoText();
            }
        };

        for (String s : digests) {
            TextView textView = (TextView) activity.getLayoutInflater().inflate(R.layout.selectable_text_view, null);
            textView.setOnClickListener(digestsClickItemListener);
            textView.setText(s);
            vSearchInContainer.addView(textView);
        }

        selectDigestsAccordingPrefs(vSearchInContainer);

        refreshSearchInInfoText();
    }

    private void selectDigestsAccordingPrefs(ViewGroup viewGroup) {
        String savedSelected = Prefs.DIGESTS_SELECTED.getValue(viewGroup.getContext());
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            TextView textView = (TextView) viewGroup.getChildAt(i);
            textView.setSelected(savedSelected.contains(textView.getText().toString()));
        }
    }

    private void saveSelectedDigests() {
        StringBuilder sb = new StringBuilder();
        List<String> selected = getSelected();
        for (int i = 0; i < selected.size(); i++) {
            sb.append(selected.get(i));
            if (i != selected.size() - 1) {
                sb.append(DIVIDER);
            }
        }
        Prefs.DIGESTS_SELECTED.setValue(activity, sb.toString());
    }

    private void refreshSearchInInfoText() {
        int count = getSelected().size();
        String msg;
        if (count == Digest.DigestType.length()) {
            msg = vSearchInInfo.getContext().getString(R.string.selected_all);
        } else if (count == 0) {
            msg = vSearchInInfo.getContext().getString(R.string.selected_none);
        } else {
            msg = vSearchInInfo.getContext().getString(R.string.selected_several);
        }
        vSearchInInfo.setText(msg);
    }

}
