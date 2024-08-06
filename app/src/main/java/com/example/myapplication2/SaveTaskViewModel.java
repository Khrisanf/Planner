package com.example.myapplication2;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class SaveTaskViewModel extends AndroidViewModel {
    private final SaveTaskRepository repository;
    private final LiveData<List<SaveTask>> allTasks;

    public SaveTaskViewModel(Application application) {
        super(application);
        repository = new SaveTaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public LiveData<List<SaveTask>> getAllTasks() {
        return allTasks;
    }

    public void insert(SaveTask task) {
        repository.insert(task);
    }

    public void update(SaveTask task) {
        repository.update(task);
    }
    public void delete(SaveTask task) {
        repository.delete(task);
    }
}
