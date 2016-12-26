package in.hedera.reku.capstone.data;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

import in.hedera.reku.capstone.utils.PollingCheck;

/**
 * Created by reku on 22/12/16.
 */

public class TestUtilities extends AndroidTestCase {

    static final long TEST_DATE = 1419033600L;  // December 20th, 2014

    static ContentValues createBillValues(){
        ContentValues billValues = new ContentValues();
        billValues.put(FFSmsContract.BillsEntry.COL_NAME, "Biller Name");
        billValues.put(FFSmsContract.BillsEntry.COL_AMOUNT, 900);
        billValues.put(FFSmsContract.BillsEntry.COL_INFO, "Account: 3020178590");
        billValues.put(FFSmsContract.BillsEntry.COL_SMSID, 1234567);
        billValues.put(FFSmsContract.BillsEntry.COL_TIME, TEST_DATE);
        return billValues;
    }

    static ContentValues createOtpValues(){
        ContentValues otpValues = new ContentValues();
        otpValues.put(FFSmsContract.OtpEntry.COL_NAME, "Aadhar");
        otpValues.put(FFSmsContract.OtpEntry.COL_INFO, " (xxx1369)");
        otpValues.put(FFSmsContract.OtpEntry.COL_OTP, "443098");
        otpValues.put(FFSmsContract.OtpEntry.COL_SMSID, 12345678);
        otpValues.put(FFSmsContract.OtpEntry.COL_TIME, TEST_DATE);
        return otpValues;
    }

    static ContentValues createPromoValues(){
        ContentValues promoValues = new ContentValues();
        promoValues.put(FFSmsContract.PromoEntry.COL_NAME, "offer Name");
        promoValues.put(FFSmsContract.PromoEntry.COL_INFO, "promo Info");
        promoValues.put(FFSmsContract.PromoEntry.COL_PROMO, 99);
        promoValues.put(FFSmsContract.PromoEntry.COL_SMSID, 123456789);
        promoValues.put(FFSmsContract.PromoEntry.COL_TIME, TEST_DATE);
        return promoValues;
    }

    static ContentValues createTravelValues(){
        ContentValues travelValues = new ContentValues();
        travelValues.put(FFSmsContract.TravelEntry.COL_FROM, "Anantapur");
        travelValues.put(FFSmsContract.TravelEntry.COL_TO, "Shirdi");
        travelValues.put(FFSmsContract.TravelEntry.COL_INFO, "Ticket 12345");
        travelValues.put(FFSmsContract.TravelEntry.COL_SMSID, 1234567890);
        travelValues.put(FFSmsContract.TravelEntry.COL_TIME, TEST_DATE);
        return travelValues;
    }

    static ContentValues createFinanceValues(){
        ContentValues financeValues = new ContentValues();
        financeValues.put(FFSmsContract.FinanceEntry.COL_INFO, "Account 0");
        financeValues.put(FFSmsContract.FinanceEntry.COL_SMSID, 12345);
        return financeValues;
    }

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    /*
        Students: The functions we provide inside of TestProvider use this utility class to test
        the ContentObserver callbacks using the PollingCheck class that we grabbed from the Android
        CTS tests.
        Note that this only tests that the onChange function is called; it does not test that the
        correct Uri is returned.
     */
    static class TestContentObserver extends ContentObserver {

        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}
