package com.example.groceryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "groceryApp.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ORDER_ID = "id";
    private static final String COLUMN_ORDER_TYPE = "type";
    private static final String COLUMN_NO_OF_ITEMS = "noofitems";
    private static final String COLUMN_ORDER_VALUE = "value";
    private static final String COLUMN_HOSTEL = "hostel";

    private static final String TABLE_ORDER_STATUS = "orderstatus";
    private static final String COLUMN_STATUS_ID = "id";
    private static final String COLUMN_PAYMENT_STATUS="payment_status";
    private static final String COLUMN_DELIVERY_STATUS="delivery_status";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableOrders = "CREATE TABLE " + TABLE_ORDERS + "(" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_TYPE + " TEXT, " +
                COLUMN_NO_OF_ITEMS + " INTEGER, " +
                COLUMN_ORDER_VALUE + " INTEGER, " +
                COLUMN_HOSTEL + " TEXT" + ")";
        String createTableOrderStatus = "CREATE TABLE " + TABLE_ORDER_STATUS + "(" +
                COLUMN_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DELIVERY_STATUS + " TEXT, " +
                COLUMN_PAYMENT_STATUS + " TEXT)";
        db.execSQL(createTableOrders);
        db.execSQL(createTableOrderStatus);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + TABLE_ORDER_STATUS + " ADD COLUMN " + COLUMN_PAYMENT_STATUS + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_ORDER_STATUS + " ADD COLUMN " + COLUMN_DELIVERY_STATUS + " TEXT");
        }
    }

    public void addOrder(orderlist order) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(COLUMN_ORDER_TYPE, order.getOrdertype());
            values.put(COLUMN_NO_OF_ITEMS, order.getNoofitems());
            values.put(COLUMN_ORDER_VALUE, order.getOrdervalue());
            values.put(COLUMN_HOSTEL, order.getHosteladdress());
            db.insert(TABLE_ORDERS, null, values);
            checkAndDeleteAOldOrders(db);
        }finally {
            db.close();
        }
    }
    public void addstatus(statuslist status){
        SQLiteDatabase db=this.getWritableDatabase();
        try{
            ContentValues values=new ContentValues();
            values.put(COLUMN_PAYMENT_STATUS,status.getPaymentstatus());
            values.put(COLUMN_DELIVERY_STATUS,status.getDeliverystatus());
            db.insert(TABLE_ORDER_STATUS,null,values);
            checkAndDeleteAOldOrders(db);
        }finally {
            db.close();
        }
    }

    public ArrayList<orderlist> getorders() {
        ArrayList<orderlist> orderList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, null, null, null, null, null, COLUMN_ORDER_ID + " DESC","2");
        if (cursor.moveToFirst()) {
            do {
                String ordertype = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_TYPE));
                int noofitems = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NO_OF_ITEMS));
                int ordervalue = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_VALUE));
                String hostel = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOSTEL));
                orderList.add(new orderlist(ordertype, noofitems, ordervalue, hostel));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orderList;
    }
    public ArrayList<statuslist> getstatus(){
        ArrayList<statuslist> statuslist=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.query(TABLE_ORDER_STATUS,null,null,null,null,null,COLUMN_STATUS_ID + " DESC","2");
        if(cursor.moveToFirst()){
            do{
                String paymentstatus= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PAYMENT_STATUS));
                String deliverystatus= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DELIVERY_STATUS));
                statuslist.add(new statuslist(paymentstatus,deliverystatus));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return statuslist;

    }
    private void checkAndDeleteAOldOrders(SQLiteDatabase db){
        Cursor cursor=null;
        Cursor statuscursor=null;

        try {
            cursor=db.query(TABLE_ORDERS,new String[]{COLUMN_ORDER_ID},null,null,null,null,COLUMN_ORDER_ID + " ASC");
            if (cursor.getCount() > 2) {
                cursor.moveToFirst();
                int deleteCount = cursor.getCount() - 2;
                while (deleteCount-- > 0 && cursor.moveToNext()) {
                    int deletedid = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ID));
                    db.delete(TABLE_ORDERS, COLUMN_ORDER_ID + "=?", new String[]{String.valueOf(deletedid)});
                }
            }
            statuscursor=db.query(TABLE_ORDER_STATUS,new String[]{COLUMN_STATUS_ID},null,null,null,null,COLUMN_STATUS_ID + " ASC");
            if(statuscursor.getCount()>2){
                statuscursor.moveToFirst();
                int deleteCount = statuscursor.getCount() - 2;
                while (deleteCount-- > 0 && statuscursor.moveToNext()) {
                    int deletedid = statuscursor.getInt(statuscursor.getColumnIndexOrThrow(COLUMN_STATUS_ID));
                    db.delete(TABLE_ORDER_STATUS, COLUMN_STATUS_ID + "=?", new String[]{String.valueOf(deletedid)});
                }
            }
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            if(statuscursor!=null){
                statuscursor.close();
            }

        }
    }
}
