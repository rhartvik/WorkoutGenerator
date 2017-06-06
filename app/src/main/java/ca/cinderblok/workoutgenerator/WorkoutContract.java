package ca.cinderblok.workoutgenerator;

import android.provider.BaseColumns;

public class WorkoutContract {

    private WorkoutContract() {}

    public static class Exercise implements BaseColumns {
        public static final String TABLE_NAME = "exercise";
        public static final String COLUMN_NAME_NAME = "exercise_name";
        public static final String COLUMN_NAME_MIN_REPS = "exercise_min_reps";
        public static final String COLUMN_NAME_MAX_REPS = "exercise_max_reps";
        public static final String COLUMN_NAME_MIN_SETS = "exercise_min_sets";
        public static final String COLUMN_NAME_MAX_SETS = "exercise_max_sets";

        public static String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_MIN_REPS + " INT,"
                + COLUMN_NAME_MAX_REPS + " INT,"
                + COLUMN_NAME_MIN_SETS + " INT,"
                + COLUMN_NAME_MAX_SETS + " INT,"
                + COLUMN_NAME_NAME + " TEXT)";
    }

    public static class BodyArea implements BaseColumns {
        public static final String TABLE_NAME = "body_area";
        public static final String COLUMN_NAME_NAME = "body_area_name";

        public static String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_NAME + " TEXT)";
    }

    public static class BodyPart implements BaseColumns {
        public static final String TABLE_NAME = "body_part";
        public static final String COLUMN_NAME_NAME = "body_part_name";
        public static final String COLUMN_NAME_FK_BODY_AREA = "body_area_fk";

        public static String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_NAME + " TEXT,"
                + COLUMN_NAME_FK_BODY_AREA + " INT,"
                + " FOREIGN KEY (" + COLUMN_NAME_FK_BODY_AREA + ") REFERENCES " + BodyArea.TABLE_NAME + "(" + BodyArea._ID + "));";
    }

    public static class ExerciseBodyPart implements BaseColumns {
        public static final String TABLE_NAME = "exercise_body_part";
        public static final String COLUMN_NAME_FK_EXERCISE = "exercise_fk";
        public static final String COLUMN_NAME_FK_BODY_PART = "body_part_fk";

        public static String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_FK_EXERCISE + " INT,"
                + COLUMN_NAME_FK_BODY_PART + " INT,"
                + " FOREIGN KEY (" + COLUMN_NAME_FK_EXERCISE + ") REFERENCES " + Exercise.TABLE_NAME + "(" + Exercise._ID + "),"
                + " FOREIGN KEY (" + COLUMN_NAME_FK_BODY_PART + ") REFERENCES " + BodyPart.TABLE_NAME + "(" + BodyPart._ID + "));";

    }

    public static class Equipment implements BaseColumns {
        public static final String TABLE_NAME = "equipment";
        public static final String COLUMN_NAME_NAME = "equipment_name";

        public static String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_NAME + " TEXT)";
    }

    public static class ExerciseEquipment implements BaseColumns {
        public static final String TABLE_NAME = "exercise_equipment";
        public static final String COLUMN_NAME_FK_EXERCISE = "exercise_fk";
        public static final String COLUMN_NAME_FK_EQUIPMENT = "equipment_fk";

        public static String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_FK_EXERCISE + " INT,"
                + COLUMN_NAME_FK_EQUIPMENT + " INT,"
                + " FOREIGN KEY (" + COLUMN_NAME_FK_EXERCISE + ") REFERENCES " + Exercise.TABLE_NAME + "(" + Exercise._ID + "),"
                + " FOREIGN KEY (" + COLUMN_NAME_FK_EQUIPMENT + ") REFERENCES " + Equipment.TABLE_NAME + "(" + Equipment._ID + "));";
    }

    public static class Gym implements BaseColumns {
        public static final String TABLE_NAME = "gym";
        public static final String COLUMN_NAME_NAME = "gym_name";

        public static String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " ("
                    + _ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_NAME_NAME + " TEXT)";
    }

    public static class GymEquipment implements BaseColumns {
        public static final String TABLE_NAME = "gym_equipment";
        public static final String COLUMN_NAME_FK_GYM = "gym_fk";
        public static final String COLUMN_NAME_FK_EQUIPMENT = "equipment_fk";

        public static String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_FK_GYM + " INT,"
                + COLUMN_NAME_FK_EQUIPMENT + " INT,"
                + " FOREIGN KEY (" + COLUMN_NAME_FK_GYM + ") REFERENCES " + Gym.TABLE_NAME + "(" + Gym._ID + "),"
                + " FOREIGN KEY (" + COLUMN_NAME_FK_EQUIPMENT + ") REFERENCES " + Equipment.TABLE_NAME + "(" + Equipment._ID + "));";
    }

}
