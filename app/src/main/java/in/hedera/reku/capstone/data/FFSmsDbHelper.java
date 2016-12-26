package in.hedera.reku.capstone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import in.hedera.reku.capstone.data.FFSmsContract.BillsEntry;
import in.hedera.reku.capstone.data.FFSmsContract.FinanceEntry;
import in.hedera.reku.capstone.data.FFSmsContract.OtpEntry;
import in.hedera.reku.capstone.data.FFSmsContract.PromoEntry;
import in.hedera.reku.capstone.data.FFSmsContract.TravelEntry;

/**
 * Created by reku on 24/11/16.
 */

public class FFSmsDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "ffsms.db";

    public FFSmsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLES = "CREATE TABLE " + BillsEntry.TABLE_NAME + " (" +
                BillsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BillsEntry.COL_AMOUNT + " INTEGER NOT NULL, " +
                BillsEntry.COL_INFO + " TEXT NOT NULL, " +
                BillsEntry.COL_NAME + " TEXT NOT NULL, " +
                BillsEntry.COL_TIME + " INTEGER NOT NULL, " +
                BillsEntry.COL_SMSID + " INTEGER NOT NULL, " +
                " UNIQUE (" + BillsEntry.COL_SMSID + ") ON CONFLICT REPLACE);" ;

        final String SQL_CREATE_TABLES1 =        "CREATE TABLE " + OtpEntry.TABLE_NAME + " (" +
                OtpEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                OtpEntry.COL_NAME + " TEXT NOT NULL, " +
                OtpEntry.COL_INFO + " TEXT NOT NULL, " +
                OtpEntry.COL_OTP + " TEXT NOT NULL, " +
                OtpEntry.COL_TIME + " INTEGER NOT NULL, " +
                OtpEntry.COL_SMSID + " INTEGER NOT NULL, " +
                " UNIQUE (" + OtpEntry.COL_SMSID + ") ON CONFLICT REPLACE);" ;

        final String SQL_CREATE_TABLES2 = "CREATE TABLE " + PromoEntry.TABLE_NAME + " (" +
                PromoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PromoEntry.COL_NAME + " TEXT NOT NULL, " +
                PromoEntry.COL_INFO + " TEXT NOT NULL, " +
                PromoEntry.COL_PROMO + " INTEGER NOT NULL, " +
                PromoEntry.COL_TIME + " INTEGER NOT NULL, " +
                PromoEntry.COL_SMSID + " INTEGER NOT NULL, " +
                " UNIQUE (" + PromoEntry.COL_SMSID + ") ON CONFLICT REPLACE);" ;

        final String SQL_CREATE_TABLES3 =         "CREATE TABLE " + TravelEntry.TABLE_NAME + " (" +
                TravelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TravelEntry.COL_FROM + " TEXT NOT NULL, " +
                TravelEntry.COL_TO  + " TEXT NOT NULL, " +
                TravelEntry.COL_INFO + " TEXT NOT NULL, " +
                TravelEntry.COL_TIME + " INTEGER NOT NULL, " +
                TravelEntry.COL_SMSID  + " INTEGER NOT NULL, " +
                " UNIQUE (" + TravelEntry.COL_SMSID + ") ON CONFLICT REPLACE);" ;

        final String SQL_CREATE_TABLES4 ="CREATE TABLE " + FinanceEntry.TABLE_NAME + " (" +
                FinanceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FinanceEntry.COL_INFO + " TEXT NOT NULL, " +
                FinanceEntry.COL_SMSID + " INTEGER NOT NULL, " +
                " UNIQUE (" + FinanceEntry.COL_SMSID + ") ON CONFLICT REPLACE);" ;

        db.execSQL(SQL_CREATE_TABLES);
        db.execSQL(SQL_CREATE_TABLES1);
        db.execSQL(SQL_CREATE_TABLES2);
        db.execSQL(SQL_CREATE_TABLES3);
        db.execSQL(SQL_CREATE_TABLES4);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        db.execSQL("DROP TABLE IF EXISTS " + BillsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OtpEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PromoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TravelEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FinanceEntry.TABLE_NAME);
        onCreate(db);
    }
}
