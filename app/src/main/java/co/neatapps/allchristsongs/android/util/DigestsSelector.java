package co.neatapps.allchristsongs.android.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.neatapps.allchristsongs.allchristiansongs.R;
import co.neatapps.allchristsongs.android.model.Digest;

public class DigestsSelector {

    private static final String DIVIDER = ";";

    private final ViewGroup viewGroup;
    private final TextView vSearchInInfo;
    private final Type type;
    private final View.OnClickListener clickItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (type) {
                case MULTI_SELECT:
                    v.setSelected(!v.isSelected());
                    break;

                case SINGLE_SELECT:
                    if (v.isSelected()) {
                        v.setSelected(false);
                    } else {
                        String selectedText = ((TextView) v).getText().toString();
                        for (int i = 0; i < viewGroup.getChildCount(); i++) {
                            TextView textView = (TextView) viewGroup.getChildAt(i);
                            textView.setSelected(selectedText.equals(textView.getText()));
                        }
                    }
                    break;
            }
            saveSelected();
            refreshSearchInInfoText();
        }
    };

    public DigestsSelector(ViewGroup viewGroup, TextView vSearchInInfo, LayoutInflater inflater) {
        this(viewGroup, vSearchInInfo, Type.MULTI_SELECT, inflater);
    }

    public DigestsSelector(ViewGroup viewGroup, TextView vSearchInInfo, Type type, LayoutInflater inflater) {
        this.viewGroup = viewGroup;
        this.vSearchInInfo = vSearchInInfo;
        this.type = type;

        List<String> digests = new ArrayList<>();
        for (int i = 0; i < Digest.DigestType.length(); i++) {
            digests.add(Digest.DigestType.values()[i].toString());
        }

        for (String s : digests) {
            TextView textView = (TextView) inflater.inflate(R.layout.selectable_text_view, null);
            textView.setOnClickListener(clickItemListener);
            textView.setText(s);
            viewGroup.addView(textView);
        }

        selectFromPrefs(viewGroup);
        refreshSearchInInfoText();
    }

    private void selectFromPrefs(ViewGroup viewGroup) {
        String savedSelected = Prefs.DIGESTS_SELECTED.getValue(viewGroup.getContext());
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            TextView textView = (TextView) viewGroup.getChildAt(i);
            textView.setSelected(savedSelected.contains(textView.getText().toString()));
        }
    }

    private void saveSelected() {
        StringBuilder sb = new StringBuilder();
        List<String> selected = getSelected();
        for (int i = 0; i < selected.size(); i++) {
            sb.append(selected.get(i));
            if (i != selected.size() - 1) {
                sb.append(DIVIDER);
            }
        }
        Prefs.DIGESTS_SELECTED.setValue(viewGroup.getContext(), sb.toString());
    }


    public List<String> getSelected() {
        ArrayList<String> selectedItems = new ArrayList<>();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            TextView textView = (TextView) viewGroup.getChildAt(i);
            if (textView.isSelected()) {
                selectedItems.add(textView.getText().toString());
            }
        }
        return selectedItems;
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

    public enum Type {
        MULTI_SELECT,
        SINGLE_SELECT
    }

}
