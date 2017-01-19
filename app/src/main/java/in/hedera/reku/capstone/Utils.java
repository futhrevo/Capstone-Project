package in.hedera.reku.capstone;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rakeshkalyankar on 19/01/17.
 */

public class Utils {

    public static String IndexArraytoString(ArrayList<String> indices){
        String[] colIndices = indices.toArray(new String[indices.size()]);
        String colIndicesString = Arrays.toString(colIndices);
        colIndicesString = colIndicesString.replace("[","(");
        colIndicesString = colIndicesString.replace("]",")");
        return colIndicesString;
    }

    public static void updatePrefValues (Cursor c, String pref, Context context){
        ArrayList<String> indices = new ArrayList<String>();
        int index = c.getColumnIndex("_id");
        for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
            indices.add(c.getString(index));
        }
        String colIndicesString = Utils.IndexArraytoString(indices);
//        Log.d("Utils", colIndicesString);
        SharedPreferences settings = context.getSharedPreferences(Main2Activity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(pref, colIndicesString);
        editor.commit();
    }

    public static String concatIndices(String... strings){
        String retString = strings[0];
        for(int i =1; i < strings.length; i++){

            String tempString = strings[i];
//            Log.d("UTILS", tempString);
            retString = retString.replace(")", ",");
            tempString = tempString.replace("(", "");

            retString = retString + tempString;
        }
        return retString;
    }
}
