package com.example.pavikhanna.databaseprac;

/**
 * Created by Pavi Khanna on 2/17/2018.
 */

public class Contract {

    public static final String DATABASE_NAME = "expenses_db";
    public static final int VERSION = 1;


    static class Expenses {

        public static final String TABLE_NAME = "expenses";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "desc";
        public static final String AMOUNT = "amount";

    }

}
