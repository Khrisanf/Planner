package com.example.myapplication2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class TaskDialogFragment extends DialogFragment {

    public interface TaskDialogListener {
        void onTaskSaved(TaskData taskData, boolean isEditing);
        void onTaskDeleted(TaskData taskData); // Добавляем метод для удаления задачи
    }

    private TaskDialogListener listener;
    private TaskData taskData;
    private boolean isEditing;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (TaskDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TaskDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_element, null);

        EditText editTitle = dialogView.findViewById(R.id.editTitle);
        EditText editDescription = dialogView.findViewById(R.id.editDescription);
        Spinner editPrioritySpinner = dialogView.findViewById(R.id.editPrioritySpinner);
        Spinner editCategorySpinner = dialogView.findViewById(R.id.editCategorySpinner);
        Button deleteButton = dialogView.findViewById(R.id.deleteButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button saveButton = dialogView.findViewById(R.id.saveButton);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editPrioritySpinner.setAdapter(priorityAdapter);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCategorySpinner.setAdapter(categoryAdapter);

        if (taskData != null) {
            isEditing = true;
            editTitle.setText(taskData.title);
            editDescription.setText(taskData.description);
            int priorityPosition = priorityAdapter.getPosition(taskData.priority);
            int categoryPosition = categoryAdapter.getPosition(taskData.category);
            editPrioritySpinner.setSelection(priorityPosition);
            editCategorySpinner.setSelection(categoryPosition);
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            isEditing = false;
            deleteButton.setVisibility(View.GONE);
        }

        builder.setView(dialogView)
                .setTitle(isEditing ? "Edit Task" : "New Task")
                .setCancelable(false);

        AlertDialog dialog = builder.create();

        saveButton.setOnClickListener(v -> {
            String title = editTitle.getText().toString();
            String description = editDescription.getText().toString();
            String priority = editPrioritySpinner.getSelectedItem().toString();
            String category = editCategorySpinner.getSelectedItem().toString();
            int color = TaskUtils.getPriorityColor(priority);

            if (taskData == null) {
                taskData = new TaskData(title, priority, category, description, color);
            } else {
                taskData.title = title;
                taskData.description = description;
                taskData.priority = priority;
                taskData.category = category;
                taskData.color = color;
            }

            if (listener != null) {
                listener.onTaskSaved(taskData, isEditing);
            }

            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        deleteButton.setOnClickListener(v -> {
            if (isEditing && taskData != null) {
                listener.onTaskDeleted(taskData); // Вызываем метод удаления
            }
            dialog.dismiss();
        });

        return dialog;
    }

    public void setTaskData(TaskData taskData) {
        this.taskData = taskData;
    }
}
