package in.hedera.reku.capstone;

import android.content.ContentResolver;
import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rakeshkalyankar on 19/01/17.
 */

public class Utils {
    // Create Inbox box URI
    public static final Uri inboxURI = Uri.parse("content://sms/inbox");

    // List required columns
    public static final String[] reqCols = new String[] { "_id", "address", "body" };

    public static final String OTPINDICES = "otpPrefInd";
    public static final String CONVERSAIONPREFINDICES = "convPrefInd";
    public static final String MISCPREFINDICES = "otherPrefInd";
    public static final String PROMOSPREFINDICES = "promoPrefInd";
    public static final String BILLPREFINDICES = "billPrefInd";
    public static final String TRAVELPREFINDICES = "travelPrefInd";
    public static final String FINANCEPREFINDICES = "financePrefInd";

    public static final int OTP_LOADER = 0;
    public static final int BILLS_LOADER = 1;

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
        if(retString == null){
            retString = "()";
        }
        for(int i =1; i < strings.length; i++){
            if(strings[i] != null){
                String tempString = strings[i];
                retString = retString.replace(")", "");
                tempString = tempString.replace("(", ",");
                retString = retString + tempString;
            }
        }
        return retString;
    }

    public static Loader<Cursor> getOTPCursorLoader(Context context){
        // Get Content Resolver object, which will deal with Content Provider
        ContentResolver cr = context.getContentResolver();
        String[] selectionArgs = {"%OTP%", "%verification code%", "%One Time Password%",
                "%Auth%code%", "%one time pin%", "%code%authentication%"}; // {".*OTP.*"}
        String selection = bodyLikeString(selectionArgs);  //"body REGEXP ?";
//        String selectionArgs = "WHERE x REGEXP <regex>";
        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor cursor = cr.query(inboxURI, reqCols, selection, selectionArgs, null);
        Loader<Cursor> loader = new CursorLoader(context, inboxURI, reqCols, selection, selectionArgs, null);
        updatePrefValues(cursor, OTPINDICES, context);
        return loader;
    }

    public static Cursor getConversationsCursor(Context context){
        // Get Content Resolver object, which will deal with Content Provider
        ContentResolver cr = context.getContentResolver();
        String selection =  "address REGEXP ?" ;//"body LIKE ? OR body LIKE ?";  //"body REGEXP ?";
        // http://regexlib.com/REDetails.aspx?regexp_id=73
        String[] selectionArgs = {"^(\\(?\\+?[0-9]*\\)?)?[0-9_\\- \\(\\)]{10,}"};//{"%OTP%", "%verification code%"}; // {".*OTP.*"}
//        String selectionArgs = "WHERE x REGEXP <regex>";
        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor cursor = cr.query(inboxURI, reqCols, selection, selectionArgs, null);
        Utils.updatePrefValues(cursor,CONVERSAIONPREFINDICES,context);
        return cursor;
    }

    public static Cursor getPromosCursor(Context context){
        // Get Content Resolver object, which will deal with Content Provider
        ContentResolver cr = context.getContentResolver();

        String[] selectionArgs = {"%buy%get%", "%!% off%", "%get%free%", "%flat%off%"}; // {".*OTP.*"}
        String selection = bodyLikeString(selectionArgs) + " ESCAPE '!'";  //"body REGEXP ?";
//        String selectionArgs = "WHERE x REGEXP <regex>";
        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor cursor = cr.query(inboxURI, reqCols, selection, selectionArgs, null);
        updatePrefValues(cursor, PROMOSPREFINDICES, context);
        return cursor;
    }

    public static Loader<Cursor> getBillsCursorLoader(Context context){
        // Get Content Resolver object, which will deal with Content Provider
        ContentResolver cr = context.getContentResolver();

        String[] selectionArgs = {"%bill%due%", "%payment%",
                "%dispatched%", "%delivered%", "%shipped%", "%AWB%", "%out for delivery%"}; // {".*OTP.*"}
        String selection = bodyLikeString(selectionArgs);  //"body REGEXP ?";
//        String selectionArgs = "WHERE x REGEXP <regex>";
        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor cursor = cr.query(inboxURI, reqCols, selection, selectionArgs, null);
        Loader<Cursor> loader = new CursorLoader(context, inboxURI, reqCols, selection, selectionArgs, null);
        updatePrefValues(cursor, BILLPREFINDICES, context);
        return loader;
    }

    public  static Cursor getTravelCursor(Context context){
        // Get Content Resolver object, which will deal with Content Provider
        ContentResolver cr = context.getContentResolver();

        String[] selectionArgs = {"%DOJ%", "%journey%departure%"}; // {".*OTP.*"}
        String selection = bodyLikeString(selectionArgs);  //"body REGEXP ?";
//        String selectionArgs = "WHERE x REGEXP <regex>";
        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor cursor = cr.query(inboxURI, reqCols, selection, selectionArgs, null);
        updatePrefValues(cursor, TRAVELPREFINDICES, context);
        return cursor;
    }

    public static Cursor getFinanceCursor(Context context){
        // Get Content Resolver object, which will deal with Content Provider
        ContentResolver cr = context.getContentResolver();
        String[] selectionArgs = {"%credit card%", "%debit card%", "%credit%A/c%", "%debit%A/c%", "%atm%"}; // {".*OTP.*"}
        String selection = bodyLikeString(selectionArgs);  //"body REGEXP ?";
//        String selectionArgs = "WHERE x REGEXP <regex>";
        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor cursor = cr.query(inboxURI, reqCols, selection, selectionArgs, null);
        updatePrefValues(cursor, FINANCEPREFINDICES, context);
        return cursor;
    }

    public static Cursor getMiscCursor(Context context){
        getOTPCursorLoader(context); //update shared preferences
        getConversationsCursor(context);
        getPromosCursor(context);
        getBillsCursorLoader(context);
        getTravelCursor(context);
        getFinanceCursor(context);

        SharedPreferences settings = context.getSharedPreferences(Main2Activity.PREFS_NAME, 0);
        String conversationIndices = settings.getString(Utils.CONVERSAIONPREFINDICES, null);
        String otpIndices = settings.getString(Utils.OTPINDICES, null);
        String promoIndices = settings.getString(Utils.PROMOSPREFINDICES, null);
        String billsIndices = settings.getString(BILLPREFINDICES, null);
        String travelIndices = settings.getString(TRAVELPREFINDICES, null);
        String financeIndices = settings.getString(FINANCEPREFINDICES, null);
        String concatIndices = concatIndices(conversationIndices,otpIndices, promoIndices,
                billsIndices, travelIndices, financeIndices);

        // Get Content Resolver object, which will deal with Content Provider
        ContentResolver cr = context.getContentResolver();

        Cursor cursor;
        if(conversationIndices == null){
            cursor = cr.query(inboxURI, reqCols, null, null, null);
        }else{
            String selection = "_id NOT IN " + concatIndices;  //"body REGEXP ?";
            String[] selectionArgs =  null;//{conversationIndices}; // {".*OTP.*"}
//        String selectionArgs = "WHERE x REGEXP <regex>";
            // Fetch Inbox SMS Message from Built-in Content Provider
            cursor = cr.query(inboxURI, reqCols, selection, selectionArgs, null);
        }

        return cursor;
    }
    
    public static String bodyLikeString(String[] strings){
        // to generate multiple LIKE statements for SQLITE query
        int len = strings.length;
        String retString = "body LIKE ?";
        if(len > 1){
            for (int i = 1; i < len; i++) {
                retString = retString + " OR body LIKE ?";
            }
        }
        return retString;
    }
}
