package ca.cinderblok.workoutgenerator.models;

import java.util.ArrayList;
import java.util.List;

public class Exercise {

    public Exercise(String name, int minReps, int maxReps, int minSets, int maxSets) {
        Name = name;
        MinReps = minReps;
        MaxReps = maxReps;
        MinSets = minSets;
        MaxSets = maxSets;

        RequiredEquipment = new ArrayList<>();
        WorkedBodyParts = new ArrayList<>();
    }

    public String Name;
    public int MinReps;
    public int MaxReps;
    public int MinSets;
    public int MaxSets;

    public List<Equipment> RequiredEquipment;
    public List<BodyPart> WorkedBodyParts;
}
