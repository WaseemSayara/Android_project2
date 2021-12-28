package edu.bzu.labproject.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import edu.bzu.labproject.Models.Car;
import edu.bzu.labproject.Models.Reservation;
import edu.bzu.labproject.Models.User;
import edu.bzu.labproject.Models.AgencyUser;


public class DatabaseHelper extends SQLiteOpenHelper {
    //Database Settings: Name and Version
    private static final String DATABASE_NAME = "CDX";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_USERS = "USERS";
    private static final String TABLE_AGENCY_USERS = "AGENCY_USERS";
    private static final String TABLE_CARS = "CARS";
    private static final String TABLE_RESERVATIONS = "RESERVATIONS";
    private static final String TABLE_FAVORITES = "FAVORITES";

    //Common Column Names
    private static final String ID_COL = "ID";
    private static final String USER_ID_COL = "USER_ID";
    private static final String CAR_ID_COL = "CAR_ID";

    //Customers Table Column Names
    private static final String EMAIL_COL = "EMAIL_ADDRESS";
    private static final String HASHED_PASSWORD_COL = "PASSWORD_HASH";
    private static final String FIRSTNAME_COL = "FIRSTNAME";
    private static final String LASTNAME_COL = "LASTNAME";
    private static final String GENDER_COL = "GENDER";
    private static final String COUNTRY_COL = "COUNTRY";
    private static final String CITY_COL = "CITY";
    private static final String PHONE_NUMBER_COL = "PHONE_NUMBER";
    private static final String NATIONALITY_COL = "NATIONALITY";
    private static final String SALARY_COL = "SALARY";
    private static final String FAMILY_SIZE_COL = "FAMILY_SIZE";
    private static final String OCCUPATION_COL = "OCCUPATION";
    private static final String AGENCY_NAME_COL = "AGENCY_NAME";

    //Cars Table Column Names
    private static final String YEAR_COL = "YEAR";
    private static final String MAKE_COL = "MAKE";
    private static final String MODEL_COL = "MODEL";
    private static final String DISTANCE_COL = "DISTANCE";
    private static final String PRICE_COL = "PRICE";
    private static final String ACCIDENTS_COL = "HAD_ACCIDENTS";

    //Reservations Table Column Names
    private static final String DATE_COL = "DATE";
    private static final String TIME_COL = "TIME";


    //SQL Statements for Tables Creation
    String SQL_CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(" + ID_COL + " INTEGER ," + EMAIL_COL + " TEXT PRIMARY KEY NOT NULL," +
            HASHED_PASSWORD_COL + " TEXT NOT NULL," + FIRSTNAME_COL + " TEXT NOT NULL," + LASTNAME_COL + " TEXT NOT NULL," +
            GENDER_COL + " TEXT NOT NULL," + COUNTRY_COL + " TEXT NOT NULL," + CITY_COL + " TEXT NOT NULL," + PHONE_NUMBER_COL + " TEXT NOT NULL," + NATIONALITY_COL +
            " TEXT NOT NULL," + SALARY_COL + " TEXT NOT NULL," + FAMILY_SIZE_COL + " TEXT NOT NULL," + OCCUPATION_COL + " TEXT NOT NULL)";

    String SQL_CREATE_TABLE_AGENCY_USERS = "CREATE TABLE " + TABLE_AGENCY_USERS + "(" + ID_COL + " INTEGER ," + EMAIL_COL + " TEXT PRIMARY KEY NOT NULL," +
            HASHED_PASSWORD_COL + " TEXT NOT NULL," + AGENCY_NAME_COL + " TEXT NOT NULL," + COUNTRY_COL + " TEXT NOT NULL," + CITY_COL +
            " TEXT NOT NULL," + PHONE_NUMBER_COL + " TEXT NOT NULL)";

    String SQL_TRY_CREATE_TABLE_AGENCY_USERS = "CREATE TABLE IF NOT EXISTS" + TABLE_USERS + "(" + ID_COL + " INTEGER ," + EMAIL_COL + " TEXT PRIMARY KEY NOT NULL," +
            HASHED_PASSWORD_COL + " TEXT NOT NULL," + AGENCY_NAME_COL + " TEXT NOT NULL," + COUNTRY_COL + " TEXT NOT NULL," + CITY_COL +
            " TEXT NOT NULL," + PHONE_NUMBER_COL + " TEXT NOT NULL)";


    String SQL_CREATE_TABLE_CARS = "CREATE TABLE " + TABLE_CARS + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + YEAR_COL + " INTEGER NOT NULL," +
            MAKE_COL + " TEXT NOT NULL," + MODEL_COL + " TEXT NOT NULL," + DISTANCE_COL + " TEXT NOT NULL," + PRICE_COL + " INTEGER NOT NULL," +
            ACCIDENTS_COL + " BOOLEAN NOT NULL)";

    String SQL_CREATE_TABLE_RESERVATIONS = "CREATE TABLE " + TABLE_RESERVATIONS + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ID_COL + " INTEGER NOT NULL," +
            CAR_ID_COL + " INTEGER NOT NULL," +
            DATE_COL + " TEXT NOT NULL," + TIME_COL + " TEXT NOT NULL)";

    String SQL_CREATE_TABLE_FAVORITES = "CREATE TABLE " + TABLE_FAVORITES + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ID_COL + " INTEGER NOT NULL,"
            + CAR_ID_COL + " INTEGER NOT NULL)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
        db.execSQL(SQL_CREATE_TABLE_AGENCY_USERS);
        db.execSQL(SQL_CREATE_TABLE_CARS);
        db.execSQL(SQL_CREATE_TABLE_RESERVATIONS);
        db.execSQL(SQL_CREATE_TABLE_FAVORITES);
        System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiii\n\n");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean addUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL_COL, user.getEmailAddress());
        contentValues.put(HASHED_PASSWORD_COL, user.getHashedPassword());
        contentValues.put(FIRSTNAME_COL, user.getFirstName());
        contentValues.put(LASTNAME_COL, user.getLastName());
        contentValues.put(GENDER_COL, user.getGender());
        contentValues.put(COUNTRY_COL, user.getCountry());
        contentValues.put(CITY_COL, user.getCity());
        contentValues.put(PHONE_NUMBER_COL, user.getPhoneNumber());
        contentValues.put(NATIONALITY_COL, user.getNationality());
        contentValues.put(SALARY_COL, user.getSalary());
        contentValues.put(FAMILY_SIZE_COL, user.getFamilySize());
        contentValues.put(OCCUPATION_COL, user.getOccupation());
        long result;
        if ((result = sqLiteDatabase.insert(TABLE_USERS, null, contentValues)) == -1)
            return false;

        else
            return true;

    }
    public boolean addAgencyUser(AgencyUser agencyUser) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL_COL, agencyUser.getEmailAddress());
        contentValues.put(HASHED_PASSWORD_COL, agencyUser.getHashedPassword());
        contentValues.put(AGENCY_NAME_COL, agencyUser.getAgencyName());
        contentValues.put(COUNTRY_COL, agencyUser.getCountry());
        contentValues.put(CITY_COL, agencyUser.getCity());
        contentValues.put(PHONE_NUMBER_COL, agencyUser.getPhoneNumber());

        long result;
        if ((result = sqLiteDatabase.insert(TABLE_AGENCY_USERS, null, contentValues)) == -1)
            return false;

        else
            return true;

    }

    public User getUserByEmailAddress(String emailAddress) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_USERS, new String[]{ID_COL, EMAIL_COL, HASHED_PASSWORD_COL,
                        FIRSTNAME_COL, LASTNAME_COL, GENDER_COL, COUNTRY_COL, CITY_COL, PHONE_NUMBER_COL},
                EMAIL_COL + "=?", new String[]{emailAddress}, null, null, null);

        if (cursor.moveToFirst()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setEmailAddress(cursor.getString(1));
            user.setHashedPassword(cursor.getString(2));
            user.setFirstName(cursor.getString(3));
            user.setLastName(cursor.getString(4));
            user.setGender(cursor.getString(5));
            user.setCountry(cursor.getString(6));
            user.setCity(cursor.getString(7));
            user.setPhoneNumber(cursor.getString(8));

            return user;
        }

        return null;
    }

    public User getCustomerById(Integer customerId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_USERS, new String[]{ID_COL, EMAIL_COL, HASHED_PASSWORD_COL,
                        FIRSTNAME_COL, LASTNAME_COL, GENDER_COL, COUNTRY_COL, CITY_COL, PHONE_NUMBER_COL},
                ID_COL + "=" + customerId, null, null, null, null);

        if (cursor.moveToFirst()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setEmailAddress(cursor.getString(1));
            user.setHashedPassword(cursor.getString(2));
            user.setFirstName(cursor.getString(3));
            user.setLastName(cursor.getString(4));
            user.setGender(cursor.getString(5));
            user.setCountry(cursor.getString(6));
            user.setCity(cursor.getString(7));
            user.setPhoneNumber(cursor.getString(8));

            return user;
        }

        return null;
    }

    public boolean updateCustomerInformation(User updatedUser, String emailAddress) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL_COL, updatedUser.getEmailAddress());
        contentValues.put(FIRSTNAME_COL, updatedUser.getFirstName());
        contentValues.put(LASTNAME_COL, updatedUser.getLastName());
        int rowsAffected = sqLiteDatabase.update(TABLE_USERS, contentValues, EMAIL_COL + " = ?", new String[]{emailAddress});
        if (rowsAffected == 0)
            return false;
        else
            return true;
    }

    public void deleteCustomerById(Integer id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_USERS, ID_COL + "=" + id, null);
    }



    public boolean userAlreadyExists(String emailAddress) {
        return (getUserByEmailAddress(emailAddress) != null);
    }

    public boolean addCar(Car car) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(YEAR_COL, car.getYearOfProduction());
        contentValues.put(MAKE_COL, car.getManufacturingCompany());
        contentValues.put(MODEL_COL, car.getCarModel());
        contentValues.put(DISTANCE_COL, car.getDistanceTraveled());
        contentValues.put(PRICE_COL, Integer.valueOf(car.getCarPrice()));
        contentValues.put(ACCIDENTS_COL, car.HadAccidents());

        long result;
        if ((result = sqLiteDatabase.insert(TABLE_CARS, null, contentValues)) != -1)
            return true;
        else
            return false;
    }

    public Car getCarById(Integer carId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_CARS,new String[]{ID_COL, YEAR_COL, MAKE_COL, MODEL_COL, DISTANCE_COL, PRICE_COL, ACCIDENTS_COL},
                ID_COL + "=" + carId, null, null, null ,null);

        if (cursor.moveToFirst()) {
            Car car = new Car();
            car.setCarId(cursor.getInt(0));
            car.setYearOfProduction(cursor.getInt(1));
            car.setManufacturingCompany(cursor.getString(2));
            car.setCarModel(cursor.getString(3));
            car.setDistanceTraveled(cursor.getString(4));
            car.setCarPrice(String.valueOf(cursor.getInt(5)));
            car.setHadAccidents(cursor.getInt(6) == 1);
            return car;
        }

        return null;


    }

    public List<Car> getAllCars() {
        List<Car> allCarsList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_CARS, new String[]{ID_COL, YEAR_COL, MAKE_COL, MODEL_COL, DISTANCE_COL, PRICE_COL, ACCIDENTS_COL},
                null,null, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Car car = new Car();
                car.setCarId(cursor.getInt(0));
                car.setYearOfProduction(cursor.getInt(1));
                car.setManufacturingCompany(cursor.getString(2));
                car.setCarModel(cursor.getString(3));
                car.setDistanceTraveled(cursor.getString(4));
                car.setCarPrice(String.valueOf(cursor.getInt(5)));
                car.setHadAccidents(cursor.getInt(6) == 1);
                allCarsList.add(car);
                cursor.moveToNext();
            }
            return allCarsList;
        }
        return null;
    }

    public List<Car> getCustomerAvailableCars(Integer customerId) {
        List<Car> allCarsList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_CARS, new String[]{ID_COL, YEAR_COL, MAKE_COL, MODEL_COL, DISTANCE_COL, PRICE_COL, ACCIDENTS_COL},
                ID_COL + " NOT IN (SELECT " + CAR_ID_COL + " FROM " + TABLE_RESERVATIONS + " WHERE " + USER_ID_COL + "=" + customerId +")" + " AND "
                        + ID_COL + " NOT IN (SELECT " + CAR_ID_COL + " FROM " + TABLE_FAVORITES + " WHERE " + USER_ID_COL + "=" + customerId +")" ,null, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Car car = new Car();
                car.setCarId(cursor.getInt(0));
                car.setYearOfProduction(cursor.getInt(1));
                car.setManufacturingCompany(cursor.getString(2));
                car.setCarModel(cursor.getString(3));
                car.setDistanceTraveled(cursor.getString(4));
                car.setCarPrice(String.valueOf(cursor.getInt(5)));
                car.setHadAccidents(cursor.getInt(6) == 1);
                allCarsList.add(car);
                cursor.moveToNext();
            }
            return allCarsList;
        }
        return null;
    }

    public List<Car> getFavoritesByCustomerId(Integer customerId) {
        List<Car> favorites = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_FAVORITES, new String[]{ID_COL, USER_ID_COL, CAR_ID_COL}, USER_ID_COL + "=" + customerId, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer carId = cursor.getInt(2);
                Car car = this.getCarById(carId);
                Log.d("FAV", car.getManufacturingCompany() + car.getCarModel());
                if (car != null)
                    favorites.add(car);

                cursor.moveToNext();
            }
            return favorites;
        }
        return null;


    }

    public boolean addFavoriteCarToCustomer(Integer customerId, Integer carId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID_COL, customerId);
        contentValues.put(CAR_ID_COL, carId);
        return sqLiteDatabase.insert(TABLE_FAVORITES, null, contentValues) != -1;
    }

    public void removeFromFavorites(Integer customerId, Integer carId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_FAVORITES, USER_ID_COL + "=" + customerId + " AND " + CAR_ID_COL + "=" + carId , null);

    }

    public boolean reserveCarByCustomer(Reservation reservation) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID_COL, reservation.getCustomerId());
        contentValues.put(CAR_ID_COL, reservation.getCarId());
        contentValues.put(DATE_COL, reservation.getDate());
        contentValues.put(TIME_COL, reservation.getTime());
        return sqLiteDatabase.insert(TABLE_RESERVATIONS, null, contentValues) != -1;
    }

    public List<Reservation> getAllReservations(){
        List<Reservation> allReservations = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_RESERVATIONS, new String[]{USER_ID_COL, CAR_ID_COL, DATE_COL, TIME_COL},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer customerId = cursor.getInt(0);
                Integer carId = cursor.getInt(1);
                String date = cursor.getString(2);
                String time = cursor.getString(3);

                Reservation reservation = new Reservation();
                reservation.setCustomerId(customerId);
                reservation.setCarId(carId);
                reservation.setDate(date);
                reservation.setTime(time);
                allReservations.add(reservation);

                cursor.moveToNext();
            }

            return allReservations;
        }

        return null;
    }

    public List<Reservation> getReservationsByCustomerId(Integer customerId){
        List<Reservation> reservationList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_RESERVATIONS, new String[]{CAR_ID_COL, DATE_COL, TIME_COL},
                USER_ID_COL + "=" + customerId, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer carId = cursor.getInt(0);
                String date = cursor.getString(1);
                String time = cursor.getString(2);

                Reservation reservation = new Reservation();
                reservation.setCarId(carId);
                reservation.setDate(date);
                reservation.setTime(time);
                reservationList.add(reservation);

                cursor.moveToNext();
            }

            return reservationList;
        }
        return null;
    }
}
