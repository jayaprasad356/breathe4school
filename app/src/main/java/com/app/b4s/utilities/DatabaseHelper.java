package com.app.b4s.utilities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.app.b4s.model.Attach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "tc.db";

    public static final String TABLE_FESTIVAL_NAME = "tblfestival";
    public static final String TABLE_USER_NAME = "tbluser";
    public static final String TABLE_ATTACH_NAME = "tblattachedQuestion";


    public static final String KEY_ID = "pid";
    final String FID = "fid";
    final String DATE = "date";
    final String FESTIVAL = "festival";

    final String UID = "uid";
    final String NAME = "name";
    final String MOBILE = "mobile";
    final String AGE = "age";

    final String QID = "qid";
    final String ATTACHMENT_LINK = "attachment_link";
    final String ANSWER = "answer";
    final String EXPLANATION = "explanation";

    final String FestivalTableInfo = TABLE_FESTIVAL_NAME + "(" + FID + " TEXT ," + DATE + " REAL ," + FESTIVAL + " TEXT)";
    final String UserTableInfo = TABLE_USER_NAME + "(" + UID + " TEXT ," + NAME + " TEXT ," + MOBILE + " TEXT ," + AGE + " TEXT )";
    final String AttachTableQuestionInfo = TABLE_ATTACH_NAME + "(" + QID + " TEXT ," + ATTACHMENT_LINK + " TEXT ," + ANSWER + " TEXT ," + EXPLANATION + " TEXT )";

    public DatabaseHelper(Activity activity) {
        super(activity, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + FestivalTableInfo);
        db.execSQL("CREATE TABLE " + UserTableInfo);
        db.execSQL("CREATE TABLE " + AttachTableQuestionInfo);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        replaceDataToNewTable(db, TABLE_FESTIVAL_NAME, FestivalTableInfo);
        replaceDataToNewTable(db, TABLE_USER_NAME, UserTableInfo);
        replaceDataToNewTable(db, TABLE_ATTACH_NAME, AttachTableQuestionInfo);

        onCreate(db);
    }

    void replaceDataToNewTable(SQLiteDatabase db, String tableName, String tableString) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableString);

        List<String> columns = getColumns(db, tableName);
        db.execSQL("ALTER TABLE " + tableName + " RENAME TO temp_" + tableName);
        db.execSQL("CREATE TABLE " + tableString);

        columns.retainAll(getColumns(db, tableName));
        String cols = join(columns);
        db.execSQL(String.format("INSERT INTO %s (%s) SELECT %s from temp_%s",
                tableName, cols, cols, tableName));
        db.execSQL("DROP TABLE temp_" + tableName);
    }

    List<String> getColumns(SQLiteDatabase db, String tableName) {
        List<String> ar = null;
        try (Cursor c = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 1", null)) {
            if (c != null) {
                ar = new ArrayList<>(Arrays.asList(c.getColumnNames()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ar;
    }

    String join(List<String> list) {
        StringBuilder buf = new StringBuilder();
        int num = list.size();
        for (int i = 0; i < num; i++) {
            if (i != 0)
                buf.append(",");
            buf.append(list.get(i));
        }
        return buf.toString();
    }


    public void deleteDb(Activity activity) {
        activity.deleteDatabase(DATABASE_NAME);

    }

    public void AddToFestival(String fid, String date, String festival) {
        try {
            if (!CheckFestivalItemExist(fid).equalsIgnoreCase("0")) {
                UpdateFestival(fid, date, festival);
            } else {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(FID, fid);
                values.put(DATE, date);
                values.put(FESTIVAL, festival);
                db.insert(TABLE_FESTIVAL_NAME, null, values);
                db.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddToUser(String uid, String name, String mobile, String age) {
        try {
            if (!CheckUserItemExist(uid).equalsIgnoreCase("0")) {
                UpdateUser(uid, name, mobile, age);
            } else {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UID, uid);
                values.put(NAME, name);
                values.put(MOBILE, mobile);
                values.put(AGE, age);

                db.insert(TABLE_USER_NAME, null, values);
                db.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddAttachQuestion(String qid, String attachmentLink, String answer, String explanation) {
        try {
            if (!CheckAttachQuestionExist(qid).equalsIgnoreCase("0")) {
                UpdateQuestion(qid, attachmentLink, answer, explanation);
            } else {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(QID, qid);
                values.put(ATTACHMENT_LINK, attachmentLink);
                values.put(ANSWER, answer);
                values.put(EXPLANATION, explanation);
                db.insert(TABLE_ATTACH_NAME, null, values);
                db.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UpdateQuestion(String qid, String attachmentLink, String answer, String explanation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QID, qid);
        values.put(ATTACHMENT_LINK, attachmentLink);
        values.put(ANSWER, answer);
        values.put(EXPLANATION, explanation);
        db.update(TABLE_ATTACH_NAME, values, QID + " = ?", new String[]{qid});
        db.close();
    }

    public void UpdateFestival(String fid, String date, String festival) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, date);
        values.put(FID, fid);
        values.put(FESTIVAL, festival);
        db.update(TABLE_FESTIVAL_NAME, values, FID + " = ?", new String[]{fid});
        db.close();
    }

    public void UpdateUser(String uid, String name, String mobile, String age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UID, uid);
        values.put(NAME, name);
        values.put(MOBILE, mobile);
        values.put(AGE, age);

        db.update(TABLE_FESTIVAL_NAME, values, FID + " = ?", new String[]{uid});
        db.close();
    }


    public ArrayList<Attach> getAnsweredQuestion(String id) {
        final ArrayList<Attach> attaches = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ATTACH_NAME + " WHERE " + QID + " = ?", new String[]{id});
        if (cursor.moveToFirst()) {
            do {
                Attach attach = new Attach(cursor.getString(cursor.getColumnIndexOrThrow(QID)), cursor.getString(cursor.getColumnIndexOrThrow(ATTACHMENT_LINK))
                        , cursor.getString(cursor.getColumnIndexOrThrow(ANSWER)), cursor.getString(cursor.getColumnIndexOrThrow(EXPLANATION)));
                //@SuppressLint("Range") String count = cursor.getString(cursor.getColumnIndex(QTY));
                attaches.add(attach);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return attaches;
    }

    public ArrayList<Attach> getmodelAttchList() {
        final ArrayList<Attach> attaches = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_ATTACH_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Attach attach = new Attach(cursor.getString(cursor.getColumnIndexOrThrow(QID)), cursor.getString(cursor.getColumnIndexOrThrow(ATTACHMENT_LINK)),
                        cursor.getString(cursor.getColumnIndexOrThrow(ANSWER)), cursor.getString(cursor.getColumnIndexOrThrow(EXPLANATION)));
                attaches.add(attach);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return attaches;
    }

    @SuppressLint("Range")
    public String CheckFestivalItemExist(String fid) {
        String count = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FESTIVAL_NAME + " WHERE " + FID + " = ?", new String[]{fid});
        if (cursor.moveToFirst()) {
            count = cursor.getString(cursor.getColumnIndex(FID));
            if (count.equals("0")) {
                db.execSQL("DELETE FROM " + TABLE_FESTIVAL_NAME + " WHERE " + FID + " = ?", new String[]{fid});

            }
        }
        cursor.close();
        db.close();
        return count;
    }

    @SuppressLint("Range")
    public String CheckUserItemExist(String uid) {
        String count = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER_NAME + " WHERE " + UID + " = ?", new String[]{uid});
        if (cursor.moveToFirst()) {
            count = cursor.getString(cursor.getColumnIndex(UID));
            if (count.equals("0")) {
                db.execSQL("DELETE FROM " + TABLE_USER_NAME + " WHERE " + UID + " = ?", new String[]{uid});

            }
        }
        cursor.close();
        db.close();
        return count;
    }

    @SuppressLint("Range")
    public String CheckAttachQuestionExist(String qid) {
        String count = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ATTACH_NAME + " WHERE " + QID + " = ?", new String[]{qid});
        if (cursor.moveToFirst()) {
            count = cursor.getString(cursor.getColumnIndex(QID));
            if (count.equals("0")) {
                db.execSQL("DELETE FROM " + TABLE_ATTACH_NAME + " WHERE " + QID + " = ?", new String[]{qid});

            }
        }
        cursor.close();
        db.close();
        return count;
    }
}