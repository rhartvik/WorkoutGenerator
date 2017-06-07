package ca.cinderblok.workoutgenerator.models;

public class BodyPart {

    public BodyPart(String name, BodyArea area) {
        Name = name;
        Area = area;
    }

    public String Name;
    public BodyArea Area;
}
