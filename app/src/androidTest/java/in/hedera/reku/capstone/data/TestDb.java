package in.hedera.reku.capstone.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

/**
 * Created by reku on 21/12/16.
 */

public class TestDb extends AndroidTestCase{
    public static final String LOG_TAG = TestDb.class.getSimpleName();

    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(FFSmsDbHelper.DATABASE_NAME);
    }

    /*
    This function gets called before each test is executed to delete the database.  This makes
    sure that we always have a clean test.
 */
    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(FFSmsContract.BillsEntry.TABLE_NAME);
        tableNameHashSet.add(FFSmsContract.PromoEntry.TABLE_NAME);
        tableNameHashSet.add(FFSmsContract.OtpEntry.TABLE_NAME);
        tableNameHashSet.add(FFSmsContract.TravelEntry.TABLE_NAME);
        tableNameHashSet.add(FFSmsContract.FinanceEntry.TABLE_NAME);

        deleteTheDatabase();

        SQLiteDatabase db = new FFSmsDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created

        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without any entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + FFSmsContract.BillsEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> billsColumnSet = new HashSet<String>();
        billsColumnSet.add(FFSmsContract.BillsEntry._ID);
        billsColumnSet.add(FFSmsContract.BillsEntry.COL_AMOUNT);
        billsColumnSet.add(FFSmsContract.BillsEntry.COL_NAME);
        billsColumnSet.add(FFSmsContract.BillsEntry.COL_INFO);
        billsColumnSet.add(FFSmsContract.BillsEntry.COL_TIME);
        billsColumnSet.add(FFSmsContract.BillsEntry.COL_SMSID);


        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            billsColumnSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                billsColumnSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + FFSmsContract.OtpEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());
        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> otpColumnSet = new HashSet<String>();
        otpColumnSet.add(FFSmsContract.OtpEntry._ID);
        otpColumnSet.add(FFSmsContract.OtpEntry.COL_INFO);
        otpColumnSet.add(FFSmsContract.OtpEntry.COL_NAME);
        otpColumnSet.add(FFSmsContract.OtpEntry.COL_OTP);
        otpColumnSet.add(FFSmsContract.OtpEntry.COL_SMSID);
        otpColumnSet.add(FFSmsContract.OtpEntry.COL_TIME);

        columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            otpColumnSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required otp entry columns",
                otpColumnSet.isEmpty());

        db.close();
    }

    public void testPromoTable(){
        // First step: Get reference to writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        FFSmsDbHelper dbHelper = new FFSmsDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step (Promo): Create Promo values
        ContentValues promoValues = TestUtilities.createPromoValues();

        // Third Step (Weather): Insert ContentValues into database and get a row ID back
        long weatherRowId = db.insert(FFSmsContract.PromoEntry.TABLE_NAME, null, promoValues);
        assertTrue(weatherRowId != -1);

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor promoCursor = db.query(
                FFSmsContract.PromoEntry.TABLE_NAME,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
        );

        // Move the cursor to the first valid database row and check to see if we have any rows
        assertTrue( "Error: No Records returned from location query", promoCursor.moveToFirst() );

        // Fifth Step: Validate the location Query
        TestUtilities.validateCurrentRecord("testInsertReadDb weatherEntry failed to validate",
                promoCursor, promoValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse( "Error: More than one record returned from weather query",
                promoCursor.moveToNext() );

        // Sixth Step: Close cursor and database
        promoCursor.close();
        dbHelper.close();
    }

    public void testOtpTable(){
        FFSmsDbHelper dbHelper = new FFSmsDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step (Promo): Create Promo values
        ContentValues promoValues = TestUtilities.createOtpValues();

        // Third Step (Weather): Insert ContentValues into database and get a row ID back
        long weatherRowId = db.insert(FFSmsContract.OtpEntry.TABLE_NAME, null, promoValues);
        assertTrue(weatherRowId != -1);

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor otpCursor = db.query(
                FFSmsContract.OtpEntry.TABLE_NAME,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
        );

        // Move the cursor to the first valid database row and check to see if we have any rows
        assertTrue( "Error: No Records returned from location query", otpCursor.moveToFirst() );

        // Fifth Step: Validate the location Query
        TestUtilities.validateCurrentRecord("testInsertReadDb weatherEntry failed to validate",
                otpCursor, promoValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse( "Error: More than one record returned from weather query",
                otpCursor.moveToNext() );

        // Sixth Step: Close cursor and database
        otpCursor.close();
        dbHelper.close();
    }
}
