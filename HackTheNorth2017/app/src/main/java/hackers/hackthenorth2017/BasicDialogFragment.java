package hackers.hackthenorth2017;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by tony on 17/09/17.
 */

public class BasicDialogFragment extends DialogFragment {
    public static BasicDialogFragment newInstance(int title) {
        BasicDialogFragment frag = new BasicDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .create();

    }
}
