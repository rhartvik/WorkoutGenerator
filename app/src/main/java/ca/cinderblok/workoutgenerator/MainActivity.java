package ca.cinderblok.workoutgenerator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import ca.cinderblok.workoutgenerator.models.BodyArea;
import ca.cinderblok.workoutgenerator.models.BodyPart;
import ca.cinderblok.workoutgenerator.models.Exercise;

public class MainActivity extends AppCompatActivity {

    private WorkoutDbHelper mDbHelper;

    private String queryForExercisesPlusBodyParts = "SELECT "
            + "e." + WorkoutContract.Exercise.COLUMN_NAME_NAME + ", "
            + "e." + WorkoutContract.Exercise.COLUMN_NAME_MIN_REPS + ", "
            + "e." + WorkoutContract.Exercise.COLUMN_NAME_MAX_REPS + ", "
            + "e." + WorkoutContract.Exercise.COLUMN_NAME_MIN_SETS + ", "
            + "e." + WorkoutContract.Exercise.COLUMN_NAME_MAX_SETS + ", "
            + "bp." + WorkoutContract.BodyPart.COLUMN_NAME_NAME + ", "
            + "ba." + WorkoutContract.BodyArea.COLUMN_NAME_NAME + " \n"
            + "FROM " + WorkoutContract.Exercise.TABLE_NAME + " AS e "
            + "JOIN " + WorkoutContract.ExerciseBodyPart.TABLE_NAME + " AS ebp\n "
            + "ON e." + WorkoutContract.Exercise._ID + " = ebp." + WorkoutContract.ExerciseBodyPart.COLUMN_NAME_FK_EXERCISE + " \n"
            + "JOIN " + WorkoutContract.BodyPart.TABLE_NAME + " AS bp\n "
            + "ON bp." + WorkoutContract.BodyPart._ID + " = ebp." + WorkoutContract.ExerciseBodyPart.COLUMN_NAME_FK_BODY_PART + " \n"
            + "JOIN " + WorkoutContract.BodyArea.TABLE_NAME + " AS ba\n "
            + "ON ba." + WorkoutContract.BodyArea._ID + " = bp." + WorkoutContract.BodyPart.COLUMN_NAME_FK_BODY_AREA + " \n";


    private String GenerateWorkout() {

        Log.w("TEST", "Generating workout");

        SQLiteDatabase dbRead = mDbHelper.getReadableDatabase();

        Cursor c = dbRead.rawQuery(queryForExercisesPlusBodyParts, new String[] {});

        Map<BodyArea,List<Exercise>> exercises = new HashMap<>();
        while(c.moveToNext()) {
            String exerciseName = c.getString(
                    c.getColumnIndexOrThrow(WorkoutContract.Exercise.COLUMN_NAME_NAME));

            int minReps = c.getInt(
                    c.getColumnIndexOrThrow(WorkoutContract.Exercise.COLUMN_NAME_MIN_REPS));
            int maxReps = c.getInt(
                    c.getColumnIndexOrThrow(WorkoutContract.Exercise.COLUMN_NAME_MAX_REPS));
            int minSets = c.getInt(
                    c.getColumnIndexOrThrow(WorkoutContract.Exercise.COLUMN_NAME_MIN_SETS));
            int maxSets = c.getInt(
                    c.getColumnIndexOrThrow(WorkoutContract.Exercise.COLUMN_NAME_MAX_SETS));
            Exercise ex = new Exercise(exerciseName, minReps, maxReps, minSets, maxSets);

            String bodyAreaName = c.getString(
                    c.getColumnIndexOrThrow(WorkoutContract.BodyArea.COLUMN_NAME_NAME));
            BodyArea area = new BodyArea(bodyAreaName);

            String bodyPartName = c.getString(
                    c.getColumnIndexOrThrow(WorkoutContract.BodyPart.COLUMN_NAME_NAME));
            BodyPart bodyPart = new BodyPart(bodyPartName, area);

            ex.WorkedBodyParts.add(bodyPart);

            if (exercises.get(area) == null) {
                exercises.put(area, new ArrayList<Exercise>());
            }
            exercises.get(area).add(ex);
        }

        Random randomNumberGenerator = ThreadLocalRandom.current();

        int numberOfExercises = 3 + randomNumberGenerator.nextInt(2);

        Object[] bodyAreas = RandomSelection(exercises.keySet().toArray(), numberOfExercises, randomNumberGenerator);

        String workout = "";
        List<Exercise> selectedExercises = new ArrayList<>();
        for (int i = 0; i < numberOfExercises; ++i) {
            List<Exercise> exercisesForThisArea = exercises.get(bodyAreas[i]);
            Exercise nextExercise = exercisesForThisArea.get(randomNumberGenerator.nextInt(exercisesForThisArea.size()));
            selectedExercises.add(nextExercise);
            workout += nextExercise.Name;
            workout += " " + (randomNumberGenerator.nextInt(nextExercise.MaxReps - nextExercise.MinReps) + nextExercise.MinReps);
            workout += "x" + (randomNumberGenerator.nextInt(nextExercise.MaxSets - nextExercise.MinSets) + nextExercise.MinSets);
            workout += "\n";
        }
        return workout;
    }

    public static <T> T[] RandomSelection(T[] population, int nSamplesNeeded, Random r) {
        if (population.length < nSamplesNeeded) {
            T[] result = (T[]) Array.newInstance(population.getClass().getComponentType(),
                    nSamplesNeeded);

            System.arraycopy(population, 0, result, 0, population.length);
            System.arraycopy(RandomSelection(population, nSamplesNeeded - population.length, r), 0, result, population.length, nSamplesNeeded - population.length);

            return result;
        } else {
            return pickRandomSample(population, nSamplesNeeded, r);
        }
    }

    // Credit: http://www.javamex.com/tutorials/random_numbers/random_sample.shtml
    public static <T> T[] pickRandomSample(T[] population, int nSamplesNeeded, Random r) {
        T[] ret = (T[]) Array.newInstance(population.getClass().getComponentType(),
                nSamplesNeeded);
        int nPicked = 0, i = 0, nLeft = population.length;
        while (nSamplesNeeded > 0) {
            int rand = r.nextInt(nLeft);
            if (rand < nSamplesNeeded) {
                ret[nPicked++] = population[i];
                nSamplesNeeded--;
            }
            nLeft--;
            i++;
        }
        return ret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbHelper = new WorkoutDbHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Let's workout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                String workout = GenerateWorkout();
                TextView text = (TextView)findViewById(R.id.text);
                text.setText(workout);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
