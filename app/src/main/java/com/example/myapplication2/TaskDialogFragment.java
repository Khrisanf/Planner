package com.example.myapplication2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TaskDialogFragment extends DialogFragment {

    private Spinner prioritySpinner;
    private Spinner categorySpinner;
    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private TaskDialogListener listener;

    public interface TaskDialogListener {
        void onTaskSaved(String title, String priority, String category, int color, String description);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (TaskDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TaskDialogListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_task, container, false);

        taskTitleEditText = view.findViewById(R.id.editTaskTitle);
        taskDescriptionEditText = view.findViewById(R.id.editTaskDescription);
        prioritySpinner = view.findViewById(R.id.priority_spinner);
        categorySpinner = view.findViewById(R.id.category_spinner);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            String taskTitle = taskTitleEditText.getText().toString();
            String taskDescription = taskDescriptionEditText.getText().toString();
            String selectedPriority = prioritySpinner.getSelectedItem().toString();
            String selectedCategory = categorySpinner.getSelectedItem().toString();
            int taskColor = getPriorityColor(selectedPriority);
            listener.onTaskSaved(taskTitle, selectedPriority, selectedCategory, taskColor, taskDescription);
            dismiss();
        });

        return view;
    }

    private int getPriorityColor(String priority) {
        switch (priority) {
            case "Очень высокий":
                return Color.parseColor("#59080E"); // black-red
            case "Высокий":
                return Color.parseColor("#AA313A"); // red
            case "Средний":
                return Color.parseColor("#FA9F4C"); //orange
            case "Низкий":
                return Color.parseColor("#8EB49E"); //green
            default:
                return Color.parseColor("#D7D7E4"); //GREY
        }
    }
}
