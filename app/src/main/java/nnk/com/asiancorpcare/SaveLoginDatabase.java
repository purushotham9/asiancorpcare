package nnk.com.asiancorpcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class SaveLoginDatabase extends SQLiteOpenHelper {
    //database helper
    public static final String DATABASE_NAME_ = "employees.db";
    public static final int DATABASE_VERSION = 1;

    //User table
    public static final String TABLE_USERS = "Employees";
    public final static String TABLE_EXPENSES = "trip_expenses";
    //users table rows
    public static final String USERS_COLUMN_ID = "ID";
    public static final String USERS_COLUMN_NAME = "emailid";
    public static final String USERS_COLUMN_PASSWORD = "password";


    public static final String te_id = "teid";
    public static final String emp_id = "emailid";
    public final static String start_km = "startkm";
    public static final String start_km_image = "imagestart";
    public final static String end_km = "endkm";
    public static final String end_km_image = "imageend";
    public static final String fuel_liter = "fuel_liter";
    public static final String expenses = "expenses";


    //quarries
    //create
    public static final String CREATE_TABLES_USERS = "create table " + TABLE_USERS + "(" + USERS_COLUMN_ID + " integer primary key," + USERS_COLUMN_NAME + " text," + USERS_COLUMN_PASSWORD + " text)";

    public static final String EXPENSES = "create table " + TABLE_EXPENSES + "(" + te_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + emp_id + "INTEGER" + start_km + " bigint," + start_km_image + " text," + end_km + " bigint," + end_km_image + " text," + fuel_liter + "INTEGER," + expenses + "INTEGER," + "FOREIGN KEY(" + emp_id + " )REFERENCES " + TABLE_USERS + "(emailid)" + ")";

    //drop
    public static final String DROP_TABLES_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;
    public static final String DROP_TABLES_EXPENSES = "DROP TABLE IF EXISTS " + TABLE_EXPENSES;

    //select rows
    public static final String SELECT_ALL_USERS = "SELECT * FROM " + TABLE_USERS;

    //array of columns
    private static String[] USER_COLUMNS = {USERS_COLUMN_ID, USERS_COLUMN_NAME, USERS_COLUMN_PASSWORD};


    public SaveLoginDatabase(Context context) {
        super(context, DATABASE_NAME_, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLES_USERS);
        db.execSQL(EXPENSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //delete old tables
        db.execSQL(DROP_TABLES_USERS);
        db.execSQL(DROP_TABLES_EXPENSES);
        //creating tables again
        onCreate(db);
    }

    public void addUser(SQLiteDatabase sqLiteDatabase, String name, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_NAME, name);
        contentValues.put(USERS_COLUMN_PASSWORD, password);
        //insert
        sqLiteDatabase.insert(TABLE_USERS, null, contentValues);
        Log.e("info >> ", "addUser : inserted ");
    }

    public String getUserInfo(SQLiteDatabase sqLiteDatabase, String name1, String password1) {
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT emailid, password FROM Employees WHERE emailid = ? AND password = ?", new String[]{name1, password1});

            // To print the entair table data
//            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users", null);
            String nameVal = null, passwordVal = null;
            if (cursor != null) {
                int i = 0;
                String nameCol[] = new String[cursor.getCount()];
                String passwordCol[] = new String[cursor.getCount()];
                if (cursor.moveToFirst()) {
                    do {
                        nameVal = cursor.getString(cursor.getColumnIndex("emailid"));
                        passwordVal = cursor.getString(cursor.getColumnIndex("password"));
                        Log.e("info >> ", "emailid  :  " + nameVal);
                        Log.e("info >> ", "password  :  " + passwordVal);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
            }
            return nameVal + passwordVal;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addExpenses(SQLiteDatabase sqLiteDatabase, int empID, int startKM, String startImage, int endKM, String endImage, int fuelLiters, int Expenses) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(emp_id, empID);
        contentValues.put(start_km, startKM);
        contentValues.put(start_km_image, startImage);
        contentValues.put(end_km, endKM);
        contentValues.put(end_km_image, endImage);
        contentValues.put(fuel_liter, fuelLiters);
        contentValues.put(expenses, Expenses);


        //insert
        sqLiteDatabase.insert(TABLE_EXPENSES, null, contentValues);
        Log.e("info >> ", "addUser : inserted ");
    }

    public String getExpenses(SQLiteDatabase sqLiteDatabase, int empid, int startkm, String startimage, int endkm, String endimage, int fuelliters, int expenses) {
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT emp_id, start_km ,start_km_image ,end_km ,end_km_image ,fuel_liter,expenses FROM Employees " + "WHERE emailid = ? AND startkm = ? AND imagestart = ? AND endkm = ? AND imageend = ? AND fuel_liter = ? AND expenses = ? ", new String[]{String.valueOf(empid), String.valueOf(startkm), startimage, String.valueOf(endkm), endimage, String.valueOf(fuelliters), String.valueOf(expenses)});

            // To print the entair table data
//            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users", null);
            String empId = null, start_Km = null, start_Image = null, end_Km = null, end_Image = null, fuel_Liter = null, exp_neses = null;
            if (cursor != null) {
                int i = 0;
                if (cursor.moveToFirst()) {
                    do {
                        empId = cursor.getString(cursor.getColumnIndex("emailid"));
                        start_Km = cursor.getString(cursor.getColumnIndex("startkm"));
                        start_Image = cursor.getString(cursor.getColumnIndex("imagestart"));
                        end_Km = cursor.getString(cursor.getColumnIndex("endkm"));
                        end_Image = cursor.getString(cursor.getColumnIndex("imageend"));
                        fuel_Liter = cursor.getString(cursor.getColumnIndex("fuel_liter"));
                        exp_neses = cursor.getString(cursor.getColumnIndex("expenses"));
                        Log.e("info >> ", "emailid  :  " + empId);
                        Log.e("info >> ", "startkm  :  " + start_Km);
                        Log.e("info >> ", "imagestart  :  " + start_Image);
                        Log.e("info >> ", "endkm  :  " + end_Km);
                        Log.e("info >> ", "imageend  :  " + end_Image);
                        Log.e("info >> ", "fuel_liter  :  " + fuel_Liter);
                        Log.e("info >> ", "expenses  :  " + exp_neses);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
            }
            return empId + start_Km + start_Image + end_Km + end_Image + fuel_Liter + exp_neses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
