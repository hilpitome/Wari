package com.warivirtualpos.wari.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.warivirtualpos.wari.model.RequestData;
import com.warivirtualpos.wari.model.WithdrawalData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hilary on 11/15/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "wari_database";
    // table names
    private static final String TABLE_TRANSFER_REQUESTS = "transfer_requests";
    private static final String TABLE_WITHDRAWAL_REQUESTS = "withdrawal_requests";

    // Common Table Columns names
    private static final String ID = "_id";
    private static final String DATE = "date";
    private static final String CONFIRMATION = "confirmation";

    // transfer_requests column names
    private static final String SENDER_LAST_NAME = "sender_last_name";
    private static final String SENDER_FIRST_NAME= "sender_first_name";
    private static final String SENDER_PHONE = "sender_phone";
    private static final String AMOUNT = "amount";
    private static final String BENEFICIARY_LAST_NAME = "beneficiary_last_name";
    private static final String BENEFICIARY_FIRST_NAME= "beneficiary_first_name";
    private static final String BENEFICIARY_PHONE = "beneficiary_phone";
    private static final String STATUS = "status";
    // withrawal_requests column names
    private static final String WITHDRAWER_LAST_NAME = "last_name";
    private static final String WITHDRAWER_FIRST_NAME = "first_name";
    private static final String WITHDARAWER_PHONE = "withdrawer_phone";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_TRANSFER_REQUESTS = "CREATE TABLE " + TABLE_TRANSFER_REQUESTS  + "("
                + ID + " INTEGER PRIMARY KEY," + DATE + " TEXT,"+ SENDER_LAST_NAME + " TEXT,"
                + SENDER_FIRST_NAME  + " TEXT," + SENDER_PHONE + " TEXT," + AMOUNT+ " INTEGER," + BENEFICIARY_LAST_NAME+ " TEXT,"+
                BENEFICIARY_FIRST_NAME+ " TEXT,"+BENEFICIARY_PHONE+" TEXT,"+CONFIRMATION+" TEXT NOT NULL DEFAULT '',"+STATUS+" TEXT"+")";

        String CREATE_TABLE_WITHDRAWAL_REQUESTS = "CREATE TABLE " + TABLE_WITHDRAWAL_REQUESTS  + "("
                + ID + " INTEGER PRIMARY KEY," + DATE +" TEXT,"+WITHDRAWER_FIRST_NAME+" TEXT,"+WITHDRAWER_LAST_NAME +" TEXT,"
                +WITHDARAWER_PHONE+" TEXT,"+CONFIRMATION+" TEXT NOT NULL DEFAULT '',"+STATUS+" TEXT"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSFER_REQUESTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_WITHDRAWAL_REQUESTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSFER_REQUESTS );
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WITHDRAWAL_REQUESTS);


        // Create tables again
        onCreate(sqLiteDatabase);
    }
    // add a new row
    public void addRequestData(RequestData requestData){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, requestData.getDate());
        contentValues.put(SENDER_LAST_NAME, requestData.getSenderLastName());
        contentValues.put(SENDER_FIRST_NAME, requestData.getSenderFirstname());
        contentValues.put(SENDER_PHONE, requestData.getSenderPhone());
        contentValues.put(BENEFICIARY_LAST_NAME, requestData.getBeneficiaryLastname());
        contentValues.put(BENEFICIARY_FIRST_NAME, requestData.getBeneficiaryFirstname());
        contentValues.put(BENEFICIARY_PHONE, requestData.getBeneficiaryPhone());
        contentValues.put(AMOUNT,requestData.getAmount());
        contentValues.put(STATUS, requestData.getStatus());
        contentValues.put(CONFIRMATION, requestData.getConfirmation());
        db.insert(TABLE_TRANSFER_REQUESTS , null, contentValues);
        db.close();
    }
    public List<RequestData> getRequestData(){

        List<RequestData> records = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_TRANSFER_REQUESTS;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            // Extract data.

            int id = cursor.getInt(cursor.getColumnIndex(ID));
            String date =  cursor.getString(cursor.getColumnIndex(DATE));
            String senderLastName = cursor.getString(cursor.getColumnIndex(SENDER_LAST_NAME));
            String senderFirstName = cursor.getString(cursor.getColumnIndex(SENDER_FIRST_NAME));
            String senderPhone = cursor.getString(cursor.getColumnIndex(SENDER_PHONE));
            int amount  = cursor.getInt(cursor.getColumnIndex(AMOUNT));
            String beneficiaryLastName = cursor.getString(cursor.getColumnIndex(BENEFICIARY_LAST_NAME));
            String beneficiaryFirstName = cursor.getString(cursor.getColumnIndex(BENEFICIARY_FIRST_NAME));
            String beneficiaryPhone = cursor.getString(cursor.getColumnIndex(BENEFICIARY_PHONE));
            String status = cursor.getString(cursor.getColumnIndex(STATUS));
            String confirmation = cursor.getString(cursor.getColumnIndex(CONFIRMATION));
            RequestData requestData= new RequestData(date, senderLastName, senderFirstName, senderPhone, amount, beneficiaryLastName, beneficiaryFirstName,
                    beneficiaryPhone, status);
            requestData.setSqliteId(id);
            requestData.setConfirmation(confirmation);
            records.add(requestData);

        }


        db.close();

        return records;
    }

    public void addWithdrawalData(WithdrawalData withdrawalData){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, withdrawalData.getDate());
        contentValues.put(WITHDRAWER_LAST_NAME, withdrawalData.getLastname());
        contentValues.put(WITHDRAWER_FIRST_NAME, withdrawalData.getFirstname());
        contentValues.put(WITHDARAWER_PHONE, withdrawalData.getPhone());
        contentValues.put(CONFIRMATION, withdrawalData.getConfirmation());
        contentValues.put(STATUS, withdrawalData.getStatus());
        db.insert(TABLE_WITHDRAWAL_REQUESTS , null, contentValues);
        db.close();
    }

    public List<WithdrawalData> getWithdrawalData(){
        List<WithdrawalData> records = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_WITHDRAWAL_REQUESTS;

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            // Extract data.
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            String date = cursor.getString(cursor.getColumnIndex(DATE));
            String withdrawerLastName = cursor.getString(cursor.getColumnIndex(WITHDRAWER_LAST_NAME));
            String withdrawerFirstName = cursor.getString(cursor.getColumnIndex(WITHDRAWER_FIRST_NAME));
            String withdrawerPhone= cursor.getString(cursor.getColumnIndex(WITHDARAWER_PHONE));
            String confirmation = cursor.getString(cursor.getColumnIndex(CONFIRMATION));
            String status = cursor.getString(cursor.getColumnIndex(STATUS));
            WithdrawalData withdrawalData =  new WithdrawalData(date, withdrawerLastName, withdrawerFirstName, withdrawerPhone, confirmation);
            withdrawalData.setSqliteId(id);
            withdrawalData.setStatus(status);
            records.add(withdrawalData);
        }

        db.close();

        return  records;
    }
    public void updateTransferRequestConfirmation(int id, String confirmation){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONFIRMATION, confirmation);
        cv.put(STATUS, "OK");
        db.update(TABLE_TRANSFER_REQUESTS, cv,ID+" = ?" ,new String[]{String.valueOf(id)});
        db.close();
    }
    public boolean updateWithdrawalConfirmation(int id){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STATUS, "OK");
        db.update(TABLE_WITHDRAWAL_REQUESTS, cv,ID+" = ?" ,new String[]{String.valueOf(id)});
        db.close();
        return true;

    }


    public RequestData getSingleRequestRecord(int Id){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSFER_REQUESTS + " WHERE "
                + ID + " = " + Id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();


        int id = cursor.getInt(cursor.getColumnIndex(ID));
        String date =  cursor.getString(cursor.getColumnIndex(DATE));
        String senderLastName = cursor.getString(cursor.getColumnIndex(SENDER_LAST_NAME));
        String senderFirstName = cursor.getString(cursor.getColumnIndex(SENDER_FIRST_NAME));
        String senderPhone = cursor.getString(cursor.getColumnIndex(SENDER_PHONE));
        int amount  = cursor.getInt(cursor.getColumnIndex(AMOUNT));
        String beneficiaryLastName = cursor.getString(cursor.getColumnIndex(BENEFICIARY_LAST_NAME));
        String beneficiaryFirstName = cursor.getString(cursor.getColumnIndex(BENEFICIARY_FIRST_NAME));
        String beneficiaryPhone = cursor.getString(cursor.getColumnIndex(BENEFICIARY_PHONE));
        String status = cursor.getString(cursor.getColumnIndex(STATUS));
        String confirmation = cursor.getString(cursor.getColumnIndex(CONFIRMATION));
        RequestData requestData= new RequestData(date, senderLastName, senderFirstName, senderPhone, amount, beneficiaryLastName, beneficiaryFirstName,
                beneficiaryPhone, status);
        requestData.setSqliteId(id);
        requestData.setConfirmation(confirmation);

        db.close();

        return  requestData;

    }
    public WithdrawalData getSingleWithdrawalRecord(int id){
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_WITHDRAWAL_REQUESTS + " WHERE "
                + ID + " = " + id;
        Log.d("singleWithdrawalData", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        int sqliteId = cursor.getInt(cursor.getColumnIndex(ID));
        String date =  cursor.getString(cursor.getColumnIndex(DATE));
        String firstName = cursor.getString(cursor.getColumnIndex(WITHDRAWER_FIRST_NAME));
        String lastName = cursor.getString(cursor.getColumnIndex(WITHDRAWER_LAST_NAME));
        String phone = cursor.getString(cursor.getColumnIndex(WITHDARAWER_PHONE));
        String confirmation = cursor.getString(cursor.getColumnIndex(CONFIRMATION));
        WithdrawalData withdrawalData = new WithdrawalData();
        withdrawalData.setSqliteId(sqliteId);
        withdrawalData.setDate(date);
        withdrawalData.setFirstname(firstName);
        withdrawalData.setLastname(lastName);
        withdrawalData.setPhone(phone);
        withdrawalData.setConfirmation(confirmation);

        db.close();

        return withdrawalData;

    }

}
