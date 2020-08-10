package project.tnguy190.calpoly.edu.detectivore.helper;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thuy on 2/26/17.
 */

public class Utilities {
    private static final String TAG = "Utilities";

    public static void initFoodItems(Context context, String fileName, ArrayList<String> foodItems) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));

            String mLine;
            while ((mLine = reader.readLine()) != null)
                foodItems.add(mLine);

        } catch (IOException e) {
            Log.d(TAG, "IOException while initializing arraylist");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d(TAG, "IOEception while closing BufferedReader");
                }
            }
        }
    }

    public static void writeToFile(Context context, ArrayList<String> items, String fileName) {
        try {
            FileOutputStream fOut = context.openFileOutput(fileName, MODE_PRIVATE);

            for (int i = 0; i < items.size() - 1; i++) {
                fOut.write((items.get(i) + ", ").getBytes());
            }

            fOut.write(items.get(items.size() - 1).getBytes());

            fOut.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToDietFile(Context context, ArrayList<String> diets) {
        writeToFile(context, diets, "diets.txt");
    }

    public static void writeToRestrictionsFile(Context context, ArrayList<String> restrictions) {
        writeToFile(context, restrictions, "animalderived.txt");
    }

    public static void writeToAllergiesFile(Context context, ArrayList<String> allergies) {
        writeToFile(context, allergies, "allergens.txt");
    }

    public static String getFromFile(Context context, String fileName) {
        StringBuilder sb = new StringBuilder();

        try {
            File file = new File(context.getFilesDir(), fileName);
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;

            if ((line = bufferedReader.readLine()) == null) {
                if (fileName.equals("diet.txt"))
                    sb.append("You have not specified your diet.");
                else if (fileName.equals("animalderived.txt"))
                    sb.append("You have not specified your restrictions.");
            }
            else {
                do {
                    sb.append(line);
                } while ((line = bufferedReader.readLine()) != null);
            }

//            if ((line = bufferedReader.readLine()) != null) {
//                sb.append(line);
//            }
//            else {
//                if (fileName.equals("diet.txt"))
//                    sb.append("You have not specified your diet.");
//                else if (fileName.equals("animalderived.txt"))
//                    sb.append("You have not specified your restrictions.");
//            }

//            while ((line = bufferedReader.readLine()) != null) {
//                sb.append(", " + line);
//            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getDiets(Context context) {
        return getFromFile(context, "diets.txt");
    }

    public static String getRestrictions(Context context) {
        // this code gets from internal storage files

//        String restrictions = getFromFile(context, "animalderived.txt");
//        Log.d("RESTRCTIONS FROM FILE: ", restrictions);
//        return restrictions;

        // this code gets from assets file
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("animalderived.txt")));

            String mLine = reader.readLine();
            while (mLine != null) {
                sb.append(mLine + " "); // process line
                mLine = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            Log.d(TAG, "IOException");
        }


        Log.d("RESTRICTIONS FROM FILE ", sb.toString());
        return sb.toString();
    }

    public static String getAllergies(Context context) {
        return getFromFile(context, "allergens.txt");
    }

    public static String[] parseList(String ing) {
        ing = ing.toLowerCase();
//        ArrayList<String> ingreds = new ArrayList<>();

        ing = ing.split(":")[1].trim(); // get rid of label

        return ing.split(", ");

//        return ingreds;
    }

    public static boolean ingredientInFile(String ing, String file, Context context) {
        StringBuilder sb = new StringBuilder();
        String mLine;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(file)));

            while ((mLine = reader.readLine()) != null) {
                if (mLine.toLowerCase().contains(ing.toLowerCase()))
                    return true;
            }
            reader.close();
        }
        catch (IOException e) {
            Log.d(TAG, "IOException");
        }

        return false;
    }
}
