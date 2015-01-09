package com.codepath.simpletodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * Created by mng on 1/8/15.
 */
public class EditTodoItemDialog extends DialogFragment implements OnEditorActionListener {
    private EditText editText;
    private EditText editDate;

    public interface EditTodoItemDialogListener {
        void onFinishEditDialog(String inputText, String date);
    }

    public EditTodoItemDialog() {

    }

    public static EditTodoItemDialog newInstance(String title) {
        EditTodoItemDialog frag = new EditTodoItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container);
        editText = (EditText) view.findViewById(R.id.editTodoName);
        editDate = (EditText) view.findViewById(R.id.editTodoDate);
        editDate.setOnEditorActionListener(this);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically
        editText.requestFocus();

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            EditTodoItemDialogListener listener = (EditTodoItemDialogListener) getActivity();
            listener.onFinishEditDialog(editText.getText().toString(), editDate.getText().toString());
            dismiss();
            return true;
        }
        return false;
    }
}
