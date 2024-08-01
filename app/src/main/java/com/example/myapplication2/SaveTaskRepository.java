package com.example.myapplication2;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class SaveTaskRepository {
    private final SaveTaskDao saveTaskDao;
    private final LiveData<List<SaveTask>> allTasks;

    SaveTaskRepository(Application application) {
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        saveTaskDao = db.saveTaskDao();
        allTasks = saveTaskDao.getAllTasks();
    }

    LiveData<List<SaveTask>> getAllTasks() {
        return allTasks;
    }

    public void insert(SaveTask task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> saveTaskDao.insert(task));
    }

    public void update(SaveTask task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> saveTaskDao.update(task));
    }

    public void delete(SaveTask task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> saveTaskDao.delete(task));
    }
}
