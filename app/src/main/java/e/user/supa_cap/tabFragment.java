package e.user.supa_cap;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class tabFragment extends Fragment {
    private final static String BACKGROUND_COLOR = "background_color";

    public static  tabFragment newInstance(@ColorRes int IdRes) {
        tabFragment frag = new tabFragment();
        Bundle b = new Bundle();
        b.putInt(BACKGROUND_COLOR, IdRes);
        frag.setArguments(b);
        return  frag;
    }

    @Override
    public void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.fragment_maim_linearLayout);
        linearLayout.setBackgroundResource(getArguments().getInt(BACKGROUND_COLOR));

        return view;

    }
}