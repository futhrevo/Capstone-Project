package in.hedera.reku.capstone.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import static in.hedera.reku.capstone.data.FFSmsContract.OtpEntry.buildOtpUri;

/**
 * Created by reku on 24/11/16.
 */

public class FFSmsProvider extends ContentProvider {
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    //    private FFSmsDbHelper mOpenHelper;
    private FFSmsDbHelper mOpenHelper;

    static final int BILLS = 100;
    static final int PROMOS = 101;
    static final int OTP = 102;
    static final int TRAVEL = 103;
    static final int FINANCE = 104;

    private static final SQLiteQueryBuilder sDbqueryBuilder;

    static {
        sDbqueryBuilder = new SQLiteQueryBuilder();

    }
    @Override
    public boolean onCreate() {
        mOpenHelper = new FFSmsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        switch (sUriMatcher.match(uri)) {
            case BILLS: {
                retCursor = db.query(FFSmsContract.BillsEntry.TABLE_NAME, null, null, null,null,null,null);
                break;
            }
            case PROMOS: {
                retCursor = db.query(FFSmsContract.PromoEntry.TABLE_NAME, null, null, null,null,null,null);
                break;
            }
            case OTP: {
                retCursor = db.query(FFSmsContract.OtpEntry.TABLE_NAME, null, null, null,null,null,null);
                break;
            }
            case TRAVEL:{
                retCursor = db.query(FFSmsContract.TravelEntry.TABLE_NAME, null, null, null, null, null, null);
                break;
            }
            case FINANCE:{
                retCursor = db.query(FFSmsContract.FinanceEntry.TABLE_NAME, null, null, null, null, null, null);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        switch (match){
            case BILLS:
                return FFSmsContract.BillsEntry.CONTENT_TYPE;
            case PROMOS:
                return FFSmsContract.PromoEntry.CONTENT_TYPE;
            case OTP:
                return FFSmsContract.OtpEntry.CONTENT_TYPE;
            case TRAVEL:
                return FFSmsContract.TravelEntry.CONTENT_TYPE;
            case FINANCE:
                return FFSmsContract.FinanceEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case BILLS: {
                long _id = db.insert(FFSmsContract.BillsEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FFSmsContract.BillsEntry.buildBillUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case PROMOS:{
                long _id = db.insert(FFSmsContract.PromoEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FFSmsContract.PromoEntry.buildPromoUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case OTP:{
                long _id = db.insert(FFSmsContract.OtpEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = buildOtpUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TRAVEL:{
                long _id = db.insert(FFSmsContract.TravelEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FFSmsContract.TravelEntry.buildTravelUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case FINANCE:{
                long _id = db.insert(FFSmsContract.FinanceEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FFSmsContract.FinanceEntry.buildFinanceUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case BILLS:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(FFSmsContract.BillsEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case PROMOS:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(FFSmsContract.PromoEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case OTP:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(FFSmsContract.OtpEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case TRAVEL:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(FFSmsContract.TravelEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case FINANCE:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(FFSmsContract.FinanceEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case BILLS:
                rowsDeleted = db.delete(FFSmsContract.BillsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PROMOS:
                rowsDeleted = db.delete(FFSmsContract.PromoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case OTP:
                rowsDeleted = db.delete(FFSmsContract.OtpEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TRAVEL:
                rowsDeleted = db.delete(FFSmsContract.TravelEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FINANCE:
                rowsDeleted = db.delete(FFSmsContract.FinanceEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case BILLS:
                rowsUpdated = db.update(FFSmsContract.BillsEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case PROMOS:
                rowsUpdated = db.update(FFSmsContract.PromoEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case OTP:
                rowsUpdated = db.update(FFSmsContract.OtpEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case TRAVEL:
                rowsUpdated = db.update(FFSmsContract.TravelEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FINANCE:
                rowsUpdated = db.update(FFSmsContract.FinanceEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    /*
        Students: Here is where you need to create the UriMatcher. This UriMatcher will
        match each URI to the WEATHER, WEATHER_WITH_LOCATION, WEATHER_WITH_LOCATION_AND_DATE,
        and LOCATION integer constants defined above.  You can test this by uncommenting the
        testUriMatcher test within TestUriMatcher.
     */
    static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FFSmsContract.CONTENT_AUTHORITY;
        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.
        matcher.addURI(authority, FFSmsContract.PATH_BILLS, BILLS);
        matcher.addURI(authority, FFSmsContract.PATH_PROMO, PROMOS);
        matcher.addURI(authority, FFSmsContract.PATH_OTP, OTP);
        matcher.addURI(authority, FFSmsContract.PATH_TRAVEL, TRAVEL);
        matcher.addURI(authority, FFSmsContract.PATH_FINANCE, FINANCE);

        // 3) Return the new matcher!
        return matcher;
    }

}
