package ca.cinderblok.workoutgenerator;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class WorkoutDbSeeder {

    // Exercises
    private static long BackSquatId;
    private static long FrontSquatId;
    private static long DeadliftId;
    private static long PullUpId;
    private static long PlankId;

    // Equipment
    private static long SquatRackId;
    private static long BarbellId;
    private static long PlatesId;
    private static long DumbellsId;
    private static long KettleBellsId;
    private static long ChinUpBarId;
    private static long MattId;

    // Body Area
    private static long CoreId;
    private static long LegsFrontId;
    private static long LegsBackId;
    private static long ArmsId;
    private static long BackId;
    private static long ChestId;

    // Body Part
    private static long AbsFrontId;
    private static long AbsObliqueId;
    private static long ShouldersId;
    private static long TriceptsId;
    private static long BiceptsId;
    private static long QuadId;
    private static long HamistringId;
    private static long CalfId;
    private static long ForearmId;
    private static long ButtocksId;
    private static long AbductorsId;
    private static long PecksId;

    // Gym
    private static long HomeGymId;
    private static long VRGymId;

    public static void SeedExercises(SQLiteDatabase db) {
        BackSquatId = AddExercise(db, "Back squat", 5, 12);
        FrontSquatId = AddExercise(db, "Front squat", 5, 12);
        DeadliftId = AddExercise(db, "Deadlift", 5, 20);
        PullUpId = AddExercise(db, "Pull up", 7, 20);
        PlankId = AddExercise(db, "Plank", 200, 4, 15, 1);

        CoreId = AddBodyArea(db, "Core");
        LegsBackId = AddBodyArea(db, "Legs back");
        LegsFrontId = AddBodyArea(db, "Legs front");
        ArmsId = AddBodyArea(db, "Arms");
        BackId = AddBodyArea(db, "Back");
        ChestId = AddBodyArea(db, "Chest");

        AbsFrontId = AddBodyPart(db, "Chest", CoreId);
        AbsObliqueId = AddBodyPart(db, "Chest", CoreId);
        ShouldersId = AddBodyPart(db, "Chest", ArmsId);
        TriceptsId = AddBodyPart(db, "Chest", ArmsId);
        BiceptsId = AddBodyPart(db, "Chest", ArmsId);
        QuadId = AddBodyPart(db, "Chest", LegsFrontId);
        HamistringId = AddBodyPart(db, "Chest", LegsBackId);
        CalfId = AddBodyPart(db, "Chest", LegsBackId);
        ForearmId = AddBodyPart(db, "Chest", ArmsId);
        ButtocksId = AddBodyPart(db, "Chest", LegsBackId);
        AbductorsId = AddBodyPart(db, "Chest", LegsBackId);
        PecksId = AddBodyPart(db, "Chest", ChestId);

        SquatRackId = AddEquipment(db, "Squat rack");
        BarbellId = AddEquipment(db, "Barbell");
        PlatesId = AddEquipment(db, "Plates");
        DumbellsId = AddEquipment(db, "Dumbells");
        KettleBellsId = AddEquipment(db, "Kettlebell");
        ChinUpBarId = AddEquipment(db, "Chin up bar");
        MattId = AddEquipment(db, "Matt");

        HomeGymId = AddGym(db, "212 Westhaven");
        VRGymId = AddGym(db, "VR Training room");

        AddExerciseBodyPart(db, BackSquatId, QuadId);
        AddExerciseBodyPart(db, BackSquatId, AbductorsId);
        AddExerciseBodyPart(db, FrontSquatId, QuadId);
        AddExerciseBodyPart(db, DeadliftId, QuadId);
        AddExerciseBodyPart(db, DeadliftId, ButtocksId);
        AddExerciseBodyPart(db, DeadliftId, HamistringId);
        AddExerciseBodyPart(db, PullUpId, ShouldersId);
        AddExerciseBodyPart(db, PullUpId, BiceptsId);
        AddExerciseBodyPart(db, PullUpId, ForearmId);
        AddExerciseBodyPart(db, PlankId, AbsFrontId);

        AddExerciseEquipment(db, BackSquatId, BarbellId);
        AddExerciseEquipment(db, BackSquatId, SquatRackId);
        AddExerciseEquipment(db, BackSquatId, PlatesId);
        AddExerciseEquipment(db, FrontSquatId, BarbellId);
        AddExerciseEquipment(db, FrontSquatId, SquatRackId);
        AddExerciseEquipment(db, FrontSquatId, PlatesId);
        AddExerciseEquipment(db, DeadliftId, BarbellId);
        AddExerciseEquipment(db, DeadliftId, PlatesId);
        AddExerciseEquipment(db, PullUpId, ChinUpBarId);
        AddExerciseEquipment(db, PlankId, MattId);

        AddGymEquipment(db, HomeGymId, BarbellId);
        AddGymEquipment(db, HomeGymId, PlatesId);
        AddGymEquipment(db, HomeGymId, SquatRackId);
        AddGymEquipment(db, HomeGymId, MattId);
        AddGymEquipment(db, HomeGymId, ChinUpBarId);
        AddGymEquipment(db, HomeGymId, DumbellsId);
        AddGymEquipment(db, VRGymId, MattId);
        AddGymEquipment(db, VRGymId, KettleBellsId);
        AddGymEquipment(db, VRGymId, ChinUpBarId);
    }

    private static long AddExercise(SQLiteDatabase db, String name, int maxReps, int maxSets) {
        return AddExercise(db, name, maxReps, maxSets, 1, 1);
    }

    private static long AddExercise(SQLiteDatabase db, String name, int maxReps, int maxSets, int minReps, int minSets) {
        ContentValues values = new ContentValues();
        values.put(WorkoutContract.Exercise.COLUMN_NAME_NAME, name);
        values.put(WorkoutContract.Exercise.COLUMN_NAME_MAX_REPS, maxReps);
        values.put(WorkoutContract.Exercise.COLUMN_NAME_MIN_REPS, minReps);
        values.put(WorkoutContract.Exercise.COLUMN_NAME_MAX_SETS, maxSets);
        values.put(WorkoutContract.Exercise.COLUMN_NAME_MIN_SETS, minSets);

        return db.insert(WorkoutContract.Exercise.TABLE_NAME, null, values);
    }

    private static long AddBodyArea(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put(WorkoutContract.BodyArea.COLUMN_NAME_NAME, name);

        return db.insert(WorkoutContract.BodyArea.TABLE_NAME, null, values);
    }

    private static long AddBodyPart(SQLiteDatabase db, String name, long bodyAreaId) {
        ContentValues values = new ContentValues();
        values.put(WorkoutContract.BodyPart.COLUMN_NAME_NAME, name);
        values.put(WorkoutContract.BodyPart.COLUMN_NAME_FK_BODY_AREA, bodyAreaId);

        return db.insert(WorkoutContract.BodyPart.TABLE_NAME, null, values);
    }

    private static long AddExerciseBodyPart(SQLiteDatabase db, long exerciseId, long bodyPartId) {
        ContentValues values = new ContentValues();
        values.put(WorkoutContract.ExerciseBodyPart.COLUMN_NAME_FK_EXERCISE, exerciseId);
        values.put(WorkoutContract.ExerciseBodyPart.COLUMN_NAME_FK_BODY_PART, bodyPartId);

        return db.insert(WorkoutContract.ExerciseBodyPart.TABLE_NAME, null, values);
    }

    private static long AddEquipment(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put(WorkoutContract.Equipment.COLUMN_NAME_NAME, name);

        return db.insert(WorkoutContract.Equipment.TABLE_NAME, null, values);
    }

    private static long AddExerciseEquipment(SQLiteDatabase db, long exerciseId, long equipmentId) {
        ContentValues values = new ContentValues();
        values.put(WorkoutContract.ExerciseEquipment.COLUMN_NAME_FK_EXERCISE, exerciseId);
        values.put(WorkoutContract.ExerciseEquipment.COLUMN_NAME_FK_EQUIPMENT, equipmentId);

        return db.insert(WorkoutContract.ExerciseEquipment.TABLE_NAME, null, values);
    }


    private static long AddGym(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put(WorkoutContract.Gym.COLUMN_NAME_NAME, name);

        return db.insert(WorkoutContract.Gym.TABLE_NAME, null, values);
    }

    private static long AddGymEquipment(SQLiteDatabase db, long gymId, long equipmentId) {
        ContentValues values = new ContentValues();
        values.put(WorkoutContract.GymEquipment.COLUMN_NAME_FK_GYM, gymId);
        values.put(WorkoutContract.GymEquipment.COLUMN_NAME_FK_EQUIPMENT, equipmentId);

        return db.insert(WorkoutContract.GymEquipment.TABLE_NAME, null, values);
    }
}
