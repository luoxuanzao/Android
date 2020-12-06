package com.byted.camp.todolist.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.byted.camp.todolist.db.dao.NoteDao;
import com.byted.camp.todolist.db.entity.MyNote;


@Database(entities = {MyNote.class}, version = 1)

public abstract class ToDoDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "ToDo.db";

    private static volatile ToDoDatabase sInstance;

    public abstract NoteDao noteDao();

    public static ToDoDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (ToDoDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static ToDoDatabase buildDatabase(Context appContext) {
        return Room.databaseBuilder(appContext, ToDoDatabase.class, DATABASE_NAME)
//                .addMigrations(MIGRATION_1_2)
                .build();
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //
        }
    };
}
