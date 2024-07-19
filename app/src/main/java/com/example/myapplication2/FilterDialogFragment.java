package com.example.myapplication2;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FilterDialogFragment extends DialogFragment {

    private Spinner spinnerCategory;
    private Spinner spinnerPriority;
    private FilterDialogListener listener;

    public interface FilterDialogListener {
        void onApplyFilters(String category, String priority);
        void onResetFilters();
    }

    public void setFilterDialogListener(FilterDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_filter, null);

        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerPriority = view.findViewById(R.id.spinnerPriority);
        Button buttonApplyFilters = view.findViewById(R.id.buttonApplyFilters);
        Button buttonResetFilters = view.findViewById(R.id.buttonResetFilters);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);

        buttonApplyFilters.setOnClickListener(v -> {
            String category = spinnerCategory.getSelectedItem().toString();
            String priority = spinnerPriority.getSelectedItem().toString();
            listener.onApplyFilters(category, priority);
            dismiss();
        });

        buttonResetFilters.setOnClickListener(v -> {
            listener.onResetFilters();
            dismiss();
        });

        buttonCancel.setOnClickListener(v -> dismiss());

        dialog.setContentView(view);
        return dialog;
    }
}
