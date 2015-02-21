package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager {

    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "particles";
    private static final String UID = "_id";
    private static final String NAME = "name";
    private static final String ABBR = "abbreviation";
    private static final String MASS = "mass";
    private static final String DENS = "density";

    private SQLHelper helper;
    private SQLiteDatabase database = null;

    public DatabaseManager(Context context) {
        this.helper = new SQLHelper(context);
    }

    private SQLiteDatabase getDatabase() {
        if (database == null) {
            database = helper.getWritableDatabase();
        }
        return database;
    }

    public void addParticle(CustomParticle particle) {
        ContentValues values = getContentValuesFromParticle(particle);
        getDatabase().insert(TABLE, NAME, values);
    }

    public void deleteParticle(int id) {
        getDatabase().delete(TABLE, UID + " = " + id, null);
    }

    public void updateParticle(int id, CustomParticle newParticle) {
        ContentValues values = getContentValuesFromParticle(newParticle);
        getDatabase().update(TABLE, values, UID + " = " + id, null);
    }

    public static ContentValues getContentValuesFromParticle(CustomParticle particle) {
        ContentValues values = new ContentValues();
        values.put(NAME, particle.getName());
        values.put(ABBR, particle.getAbbreviation());
        values.put(MASS, particle.getMass());
        values.put(DENS, particle.getDensity());

        return values;
    }

    public CustomParticle getParticleWithId(int id) {
        Cursor cursor = getDatabase().query(TABLE, null, UID + " = " + id, null, null, null, null);
        if (cursor.moveToNext()) {
            final String name = cursor.getString(cursor.getColumnIndex(NAME));
            final String abbr = cursor.getString(cursor.getColumnIndex(ABBR));
            final Double mass = cursor.getDouble(cursor.getColumnIndex(MASS));
            final Double dens = cursor.getDouble(cursor.getColumnIndex(DENS));

            cursor.close();
            return new CustomParticle(name, abbr, mass, dens);
        }
        cursor.close();
        return null;
    }

    public CustomParticle[] getParticles() {
        Cursor cursor = getDatabase().query(TABLE, null, null, null, null, null, UID + " ASC");
        CustomParticle[] particles = new CustomParticle[cursor.getCount()];

        final int UID_C = cursor.getColumnIndex(UID);
        final int NAME_C = cursor.getColumnIndex(NAME);
        final int ABBR_C = cursor.getColumnIndex(ABBR);
        final int MASS_C = cursor.getColumnIndex(MASS);
        final int DENS_C = cursor.getColumnIndex(DENS);

        while (cursor.moveToNext()) { // cursor is initially at position before first result
            final int id = cursor.getInt(UID_C);
            final String name = cursor.getString(NAME_C);
            final String abbr = cursor.getString(ABBR_C);
            final Double mass = cursor.getDouble(MASS_C);
            final Double dens = cursor.getDouble(DENS_C);

            particles[id - 1] = new CustomParticle(name, abbr, mass, dens);
        }

        cursor.close();

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
                db.execSQL("DROP TABLE IF EXISTS " + TABLE + ";");
                onCreate(db);
            } catch (SQLiteException ignored) {
            }
        }
    }
}
