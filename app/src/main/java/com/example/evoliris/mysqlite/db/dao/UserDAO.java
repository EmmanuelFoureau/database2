package com.example.evoliris.mysqlite.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.evoliris.mysqlite.db.DbHelper;
import com.example.evoliris.mysqlite.models.User;

import java.util.Locale;

public class UserDAO {
    public static final String TABLE_NAME = "user";
    public static final String COL_ID = "user_id";
    public static final String COL_FIRSTNAME = "user_firstname";
    public static final String COL_NAME = "user_name";
    public static final String COL_ADRESSE = "user_adresse";

    public static final String CREATE = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(50) NOT NULL, %s VARCHAR(50) NOT NULL, %s VARCHAR(255)NOT NULL);", TABLE_NAME, COL_ID, COL_FIRSTNAME, COL_NAME, COL_ADRESSE);
    public static final String DROP = String.format("DROP TABLE %s;", TABLE_NAME);

    private DbHelper helper;
    private Context context;
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        this.context = context;
        this.helper = new DbHelper(this.context);
    }

    public UserDAO openReadable() {
        this.db = this.helper.getReadableDatabase();
        return this;
    }

    public UserDAO openWritable() {
        this.db = this.helper.getWritableDatabase();
        return this;
    }

    public void close () {
        db.close();
        helper.close();
    }

    public User findById(long id){
        //SELECT * FROM user WHERE user_id = 2;
        Cursor cursor = db.query(TABLE_NAME,null,COL_ID+ " = "+ id,null, null, null, null);
        if(cursor.getCount() <= 0)return null;
        cursor.moveToFirst();


        return from(cursor);
    }

    private User from(Cursor cursor){

        int colIdIndex = cursor.getColumnIndex(COL_ID);
        int colNameIndex = cursor.getColumnIndex(COL_NAME);
        int colFirstNameIndex = cursor.getColumnIndex(COL_FIRSTNAME);
        int colAdresseIndex = cursor.getColumnIndex(COL_ADRESSE);

        long userId = cursor.getLong(colIdIndex);
        String userName = cursor.getString(colNameIndex);
        String userFirstname = cursor.getString(colFirstNameIndex);
        String userAdresse = cursor.getString(colAdresseIndex);

        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setFirstName(userFirstname);
        user.setAdresse(userAdresse);

        return user;
    }

    public long insert(User user) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_FIRSTNAME, user.getFirstName());
        values.put(COL_ADRESSE, user.getAdresse());

        long idInserted = db.insert(TABLE_NAME, null,values);
        user.setId(idInserted);
        return idInserted;
    }

    public void update(User user){
        ContentValues values = new ContentValues();
        values.put(COL_ID , user.getId());
        values.put(COL_NAME, user.getName());
        values.put(COL_FIRSTNAME, user.getFirstName());
        values.put(COL_ADRESSE, user.getAdresse());

        db.update(TABLE_NAME, values,String.format(Locale.FRENCH, "%s = %d", COL_ID, user.getId()), null);
    }

}
