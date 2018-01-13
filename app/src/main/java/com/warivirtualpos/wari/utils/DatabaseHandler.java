package com.warivirtualpos.wari.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.warivirtualpos.wari.model.Agent;
import com.warivirtualpos.wari.model.TransferRequestData;
import com.warivirtualpos.wari.model.WithdrawalData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hilary on 11/15/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 10;

    // Database Name
    private static final String DATABASE_NAME = "wari_database";
    // table names
    private static final String TABLE_TRANSFER_REQUESTS = "transfer_requests";
    private static final String TABLE_WITHDRAWAL_REQUESTS = "withdrawal_requests";
    private static final String TABLE_VIRTUAL_AGENTS = "virtual_agents";

    // Common Table Columns names
    private static final String ID = "_id";
    private static final String DATE = "date";
    private static final String CONFIRMATION = "confirmation";
    private static final String AGENT_NUMBER ="agent_number";
    private static final String AGENT_NAME = "agent_name";
    private static final String ONLINE_UPDATED = "online_updated";


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

    // virtual agents column names

    private static final String AGENT_BALANCE = "agent_balance";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_TRANSFER_REQUESTS = "CREATE TABLE " + TABLE_TRANSFER_REQUESTS  + "("
                + ID + " INTEGER PRIMARY KEY," + AGENT_NUMBER +" TEXT NOT NULL DEFAULT '',"+ AGENT_NAME +" TEXT NOT NULL DEFAULT '',"+DATE + " TEXT,"+ SENDER_LAST_NAME + " TEXT,"
                + SENDER_FIRST_NAME  + " TEXT," + SENDER_PHONE + " TEXT," + AMOUNT+ " INTEGER," + BENEFICIARY_LAST_NAME+ " TEXT,"+
                BENEFICIARY_FIRST_NAME+ " TEXT,"+BENEFICIARY_PHONE+" TEXT,"+CONFIRMATION+" TEXT NOT NULL DEFAULT '',"+ONLINE_UPDATED +" TEXT NOT NULL DEFAULT 'false',"+STATUS+" TEXT"+")";

        String CREATE_TABLE_WITHDRAWAL_REQUESTS = "CREATE TABLE " + TABLE_WITHDRAWAL_REQUESTS  + "("
                + ID + " INTEGER PRIMARY KEY," +AGENT_NUMBER +" TEXT NOT NULL DEFAULT '',"+ AGENT_NAME +" TEXT NOT NULL DEFAULT '',"+DATE +" TEXT,"+WITHDRAWER_FIRST_NAME+" TEXT,"+WITHDRAWER_LAST_NAME +" TEXT,"
                +WITHDARAWER_PHONE+" TEXT,"+CONFIRMATION+" TEXT NOT NULL DEFAULT '',"+AMOUNT +" TEXT NOT NULL DEFAULT '',"+ONLINE_UPDATED +" TEXT NOT NULL DEFAULT 'false',"+STATUS+" TEXT"+")";
        String CREATE_TABLE_VIRTUAL_AGENTS = "CREATE TABLE " + TABLE_VIRTUAL_AGENTS +"("+ID+" INTEGER PRIMARY KEY,"+ AGENT_NUMBER +" TEXT NOT NULL DEFAULT '',"+AGENT_NAME +" TEXT,"+AGENT_BALANCE +" TEXT NOT NULL DEFAULT ''"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSFER_REQUESTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_WITHDRAWAL_REQUESTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_VIRTUAL_AGENTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSFER_REQUESTS );
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WITHDRAWAL_REQUESTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VIRTUAL_AGENTS);


        // Create tables again
        onCreate(sqLiteDatabase);
    }
    // add a new row
    public void addRequestData(TransferRequestData transferRequestData){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, transferRequestData.getDate());
        contentValues.put(SENDER_LAST_NAME, transferRequestData.getSenderLastName());
        contentValues.put(SENDER_FIRST_NAME, transferRequestData.getSenderFirstname());
        contentValues.put(SENDER_PHONE, transferRequestData.getSenderPhone());
        contentValues.put(BENEFICIARY_LAST_NAME, transferRequestData.getBeneficiaryLastname());
        contentValues.put(BENEFICIARY_FIRST_NAME, transferRequestData.getBeneficiaryFirstname());
        contentValues.put(BENEFICIARY_PHONE, transferRequestData.getBeneficiaryPhone());
        contentValues.put(AMOUNT, transferRequestData.getAmount());
        contentValues.put(STATUS, transferRequestData.getStatus());
        contentValues.put(CONFIRMATION, transferRequestData.getConfirmation());
        contentValues.put(AGENT_NUMBER, transferRequestData.getAgentNumber());
        db.insert(TABLE_TRANSFER_REQUESTS , null, contentValues);
        db.close();
    }
    public List<TransferRequestData> getTransferRequestData(){

        List<TransferRequestData> records = new ArrayList<>();
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
            String agentNumber = cursor.getString(cursor.getColumnIndex(AGENT_NUMBER));
            TransferRequestData transferRequestData = new TransferRequestData(date, senderLastName, senderFirstName, senderPhone, amount, beneficiaryLastName, beneficiaryFirstName,
                    beneficiaryPhone, status);
            transferRequestData.setSqliteId(id);
            transferRequestData.setConfirmation(confirmation);
            transferRequestData.setAgentNumber(agentNumber);
            records.add(transferRequestData);

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
        contentValues.put(AGENT_NUMBER, withdrawalData.getAgentNumber());
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
    public void updateTransferRequestConfirmation(int id, String confirmation, String onlineUpdated){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONFIRMATION, confirmation);
        cv.put(STATUS, "OK");
        cv.put(ONLINE_UPDATED, onlineUpdated);
        db.update(TABLE_TRANSFER_REQUESTS, cv,ID+" = ?" ,new String[]{String.valueOf(id)});
        db.close();
    }
    public void updateTransferRequestOnlineOnlineUpdated(int id){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ONLINE_UPDATED, "true");
        cv.put(STATUS, "OK");
        db.update(TABLE_TRANSFER_REQUESTS, cv,ID+" = ?" ,new String[]{String.valueOf(id)});
        db.close();
    }
    public void updateWithdrawalDataAmount(int id, String amount){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AMOUNT, amount);

        db.update(TABLE_WITHDRAWAL_REQUESTS, cv,ID+" = ?" ,new String[]{String.valueOf(id)});
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


    public TransferRequestData getSingleRequestRecord(int Id){
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
        String agentNumber = cursor.getString(cursor.getColumnIndex(AGENT_NUMBER));
        TransferRequestData transferRequestData = new TransferRequestData(date, senderLastName, senderFirstName, senderPhone, amount, beneficiaryLastName, beneficiaryFirstName,
                beneficiaryPhone, status);
        transferRequestData.setSqliteId(id);
        transferRequestData.setConfirmation(confirmation);
        transferRequestData.setAgentNumber(agentNumber);

        db.close();

        return transferRequestData;

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
        String agentNumber = cursor.getString(cursor.getColumnIndex(AGENT_NUMBER));
        WithdrawalData withdrawalData = new WithdrawalData();
        withdrawalData.setSqliteId(sqliteId);
        withdrawalData.setDate(date);
        withdrawalData.setFirstname(firstName);
        withdrawalData.setLastname(lastName);
        withdrawalData.setPhone(phone);
        withdrawalData.setConfirmation(confirmation);
        withdrawalData.setAgentNumber(agentNumber);
        db.close();

        return withdrawalData;

    }

    public List<Agent> getAgentsData(){

        List<Agent> agentsList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_VIRTUAL_AGENTS;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            // Extract data.
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            String agentNumber = cursor.getString(cursor.getColumnIndex(AGENT_NUMBER));
            int agentBalance = cursor.getInt(cursor.getColumnIndex(AGENT_BALANCE));

            Agent agent = new Agent(agentNumber, agentBalance);
            agent.setSqliteId(id);
            agentsList.add(agent);
        }

        return agentsList;

    }
    // add a new row
    public void addAgentDetails(Agent agent){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AGENT_NUMBER, agent.getSdNumber());
        contentValues.put(AGENT_NAME, agent.getSdName());
        contentValues.put(AGENT_BALANCE, agent.getSdBalance());
        db.insert(TABLE_VIRTUAL_AGENTS , null, contentValues);
        db.close();
    }

    public Agent checkIfAgent(String phoneNumber) {
        SQLiteDatabase sqliteDatabase = getReadableDatabase();
        Agent agent=null;
        Cursor cursor = sqliteDatabase.rawQuery("SELECT * FROM "+TABLE_VIRTUAL_AGENTS+" WHERE "+AGENT_NUMBER+" = ?", new String[] {phoneNumber});

        if (cursor.getCount()>0) {

            cursor.moveToNext();
            agent = new Agent();
            agent.setSdNumber(phoneNumber);
            agent.setSdBalance(cursor.getInt(cursor.getColumnIndex(AGENT_BALANCE)));
        }


        sqliteDatabase.close();

        return agent;

    }

    public void updateSqliteBalance(int amount, String agentNumber) {
        SQLiteDatabase sqliteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AGENT_BALANCE, amount);
        sqliteDatabase.update(TABLE_VIRTUAL_AGENTS, cv,AGENT_NUMBER+" = ?" ,new String[]{agentNumber});
        sqliteDatabase.close();
    }


}
