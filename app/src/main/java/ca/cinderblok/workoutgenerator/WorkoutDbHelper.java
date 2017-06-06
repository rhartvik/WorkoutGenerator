package ca.cinderblok.workoutgenerator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorkoutDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workout.db";

    private static String SQL_DELETE_TABLE(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName;
    }

    public WorkoutDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WorkoutContract.Exercise.CREATE_STATEMENT);

        db.execSQL(WorkoutContract.BodyArea.CREATE_STATEMENT);
        db.execSQL(WorkoutContract.BodyPart.CREATE_STATEMENT);
        db.execSQL(WorkoutContract.ExerciseBodyPart.CREATE_STATEMENT);

        db.execSQL(WorkoutContract.Equipment.CREATE_STATEMENT);
        db.execSQL(WorkoutContract.ExerciseEquipment.CREATE_STATEMENT);

        db.execSQL(WorkoutContract.Gym.CREATE_STATEMENT);
        db.execSQL(WorkoutContract.GymEquipment.CREATE_STATEMENT);

        WorkoutDbSeeder.SeedExercises(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE(WorkoutContract.Exercise.TABLE_NAME));

        db.execSQL(SQL_DELETE_TABLE(WorkoutContract.BodyArea.TABLE_NAME));
        db.execSQL(SQL_DELETE_TABLE(WorkoutContract.BodyPart.TABLE_NAME));
        db.execSQL(SQL_DELETE_TABLE(WorkoutContract.ExerciseBodyPart.TABLE_NAME));

        db.execSQL(SQL_DELETE_TABLE(WorkoutContract.Equipment.TABLE_NAME));
        db.execSQL(SQL_DELETE_TABLE(WorkoutContract.ExerciseEquipment.TABLE_NAME));

        db.execSQL(SQL_DELETE_TABLE(WorkoutContract.Gym.TABLE_NAME));
        db.execSQL(SQL_DELETE_TABLE(WorkoutContract.GymEquipment.TABLE_NAME));
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
