package eli.wearlab.capturefacecompanion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "faceinfo.db";
    private static final String TABLE_NAME = "Face_Data";
    public static final String FACE_NAME = "Face_Name";
    public static final String FACE_ID = "Face_ID";
    public static final String FACE_INFO = "Face_Info"; //Holds description that user has input for a particular face

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + FACE_NAME + " TEXT, "
                + FACE_ID + " TEXT, "
                + FACE_INFO + " TEXT"
                + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*public void addWord(Word word) { //Write example
        ContentValues values = new ContentValues();
        values.put(VOCAB_INPUT, word.getWord());
        values.put(VOCAB_TRANSLATION, word.getTranslation());
        values.put(DATE_TO_REMIND, word.getDate());
        values.put(VOCAB_HINT, word.getHint());
        values.put(DAY_WAITS, Arrays.toString(word.getDayWaitsArray()));
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }*/

    /*public List<Word> getWordsToReview(long date) { //Read example
        List<Word> returnWords = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + DATE_TO_REMIND + " <= '" + date + "'", null);
        while (cursor.moveToNext()) {
            String retrievedName = cursor.getString(cursor.getColumnIndex(VOCAB_INPUT));
            String retrievedTranslation = cursor.getString(cursor.getColumnIndex(VOCAB_TRANSLATION));
            String retrievedHint = cursor.getString(cursor.getColumnIndex(VOCAB_HINT));
            long retrievedDate = cursor.getLong(cursor.getColumnIndex(DATE_TO_REMIND));
            int[] dayWaitsArray = backToArray(cursor.getString(cursor.getColumnIndex(DAY_WAITS)));
            returnWords.add(new Word(retrievedName, retrievedTranslation, retrievedHint, retrievedDate, dayWaitsArray));
        }
        cursor.close();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + DATE_TO_REMIND + " <= '" + date + "'");
        return returnWords;
    }*/

    /*Other needed methods:
        - Parse integer list into ID string and back again
        - (For searching) Get all listings that have a name input and return them and their picture (Store as a field the storage location?)
        - Check an incoming picture/ID against the database and:
            - If found matching/similar enough ID, send the data found back to the glasses
            - If not found, add a new listing with only ID/picture to the database
        - Get all listings without full information (to be sent to main activity for input)
        - Submit list of faces to be resubmitted to the database
            - After checking in main activity, so we know it will be in the database already
            - Update fields in database with any new information
        */

}