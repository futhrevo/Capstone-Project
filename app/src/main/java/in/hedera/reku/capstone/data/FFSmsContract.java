package in.hedera.reku.capstone.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by reku on 24/11/16.
 */

public class FFSmsContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "in.hedera.reku.capstone";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_BILLS = "bills";
    public static final String PATH_FINANCE = "finance";
    public static final String PATH_OTP = "otp";
    public static final String PATH_PROMO = "promos";
    public static final String PATH_TRAVEL = "travels";

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static final class BillsEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BILLS).build();

        public static final String TABLE_NAME = "bills";

        public static final String COL_AMOUNT = "billAmount";
        public static final String COL_NAME = "billerName";
        public static final String COL_INFO = "billDetails";
        public static final String COL_TIME = "billTime";
        public static final String COL_SMSID = "billSMSId";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BILLS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BILLS;

        public static Uri buildBillUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class FinanceEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FINANCE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FINANCE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FINANCE;

        public static final String TABLE_NAME = "finance";
        public static final String COL_SMSID = "finSMSId";
        public static final String COL_INFO = "finDetails";

        public static Uri buildFinanceUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    public static final class OtpEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_OTP).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_OTP;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_OTP;

        public static final String TABLE_NAME = "otp";

        public static final String COL_OTP = "otpVerb";
        public static final String COL_NAME = "otpName";
        public static final String COL_INFO = "otpDetails";
        public static final String COL_TIME = "otpTime";
        public static final String COL_SMSID = "otpSMSId";

        public static Uri buildOtpUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    public static final class PromoEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROMO).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROMO;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROMO;

        public static final String TABLE_NAME = "promos";

        public static final String COL_PROMO = "promoPercent";
        public static final String COL_NAME = "promoName";
        public static final String COL_INFO = "promoDetails";
        public static final String COL_TIME = "promoTime";
        public static final String COL_SMSID = "promoSMSId";

        public static Uri buildPromoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
    public static final class TravelEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAVEL).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAVEL;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAVEL;
        public static final String TABLE_NAME = "travels";

        public static final String COL_TIME = "travelDoj";
        public static final String COL_FROM = "travelFrom";
        public static final String COL_TO = "travelDest";
        public static final String COL_INFO = "travelDetails";
        public static final String COL_SMSID = "travelSMSId";

        public static Uri buildTravelUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
