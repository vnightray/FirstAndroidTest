package com.example.myfirstapplication.database;

public class BookDbSchema {

    public static final class BookTable{

        public static final String NAME = "book";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String DATE = "date";
            public static final String READ = "read";
            public static final String OWNER = "owner";

        }

    }
}
