package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager {

    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "particles";
    public static final String UID = "_id";
    public static final String NAME = "name";
    public static final String ABBR = "abbreviation";
    public static final String MASS = "mass";
    public static final String DENS = "density";

    private SQLHelper helper;
    private SQLiteDatabase database = null;

    public DatabaseManager(Context context) {
        this.helper = new SQLHelper(context);
    }

    public SQLiteDatabase getDatabase() {
        if (database == null) {
            database = helper.getWritableDatabase();
        }
        return database;
    }

    public void addParticle(CustomParticle particle) {
        ContentValues values = new ContentValues();
        values.put(NAME, particle.getName());
        values.put(ABBR, particle.getAbbreviation());
        values.put(MASS, particle.getMass());
        values.put(DENS, particle.getDensity());

        getDatabase().insert(TABLE, NAME, values);
    }

    public void deleteParticle(int index) {
        getDatabase().delete(TABLE, UID + "=" + index, null);
    }

    public CustomParticle[] getParticles() {
        Cursor cursor = getDatabase().rawQuery("SELECT * FROM " + TABLE, null);
        CustomParticle[] particles = new CustomParticle[cursor.getCount()];

        final int NAME_C = cursor.getColumnIndex(NAME);
        final int ABBR_C = cursor.getColumnIndex(ABBR);
        final int MASS_C = cursor.getColumnIndex(MASS);
        final int DENS_C = cursor.getColumnIndex(DENS);

        cursor.moveToFirst();
        for (int i = 0; i < particles.length; i++) {
            cursor.moveToPosition(i);

            final String name = cursor.getString(NAME_C);
            final String abbr = cursor.getString(ABBR_C);
            final Double mass = cursor.getDouble(MASS_C);
            final Double dens = cursor.getDouble(DENS_C);

            particles[i] = new CustomParticle(name, abbr, mass, dens);
        }

        return particles;
    }


    public static class SQLHelper extends SQLiteOpenHelper {
        Context context;

        public SQLHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE + " (" +
                    UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " VARCHAR(255), " +
                    ABBR + " VARCHAR(63), " +
                    MASS + " DOUBLE, " +
                    DENS + " DOUBLE);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE);
                onCreate(db);
            } catch (SQLiteException ignored) {
            }
        }
    }
}
