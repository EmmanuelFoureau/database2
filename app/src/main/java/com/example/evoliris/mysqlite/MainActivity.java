package com.example.evoliris.mysqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.evoliris.mysqlite.db.dao.UserDAO;
import com.example.evoliris.mysqlite.models.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserDAO userDAO = new UserDAO(MainActivity.this);
        userDAO.openWritable();

        User u = new User();
        u.setName("Emmanuel");
        u.setFirstName("Foureau");
        u.setAdresse("Ittre");

        long id = userDAO.insert(u);

        userDAO.close();
    }
}
