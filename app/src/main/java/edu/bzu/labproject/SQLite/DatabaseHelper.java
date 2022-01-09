package edu.bzu.labproject.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import edu.bzu.labproject.Models.Car;
import edu.bzu.labproject.Models.House;
import edu.bzu.labproject.Models.Reservation;
import edu.bzu.labproject.Models.User;
import edu.bzu.labproject.Models.AgencyUser;


public class DatabaseHelper extends SQLiteOpenHelper {
    //Database Settings: Name and Version
    private static final String DATABASE_NAME = "MyHouse";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_USERS = "USERS";
    private static final String TABLE_AGENCY_USERS = "AGENCY_USERS";
    private static final String TABLE_CARS = "CARS";
    private static final String TABLE_HOUSES = "HOUSES";
    private static final String TABLE_RESERVATIONS = "RESERVATIONS";
    private static final String TABLE_FAVORITES = "FAVORITES";

    //Common Column Names
    private static final String ID_COL = "ID";
    private static final String USER_ID_COL = "USER_ID";
    private static final String AGENCY_USER_ID_COL = "AGENCY_ID";
    private static final String CAR_ID_COL = "CAR_ID";
    private static final String HOUSE_ID_COL = "HOUSE_ID";
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
    private static final String ACCIDENTS_COL = "HAD_ACCIDENTS";

    //HOUSES Table Column Names
    private static final String POSTAL_ADDRESS_COL = "POSTAL_ADDRESS";
    private static final String AREA_COL = "AREA";
    private static final String CONSTRUCTION_COL = "CONSTRUCTION";
    private static final String BEDROOMS_COL = "BEDROOMS";
    private static final String PRICE_COL = "PRICE";
    private static final String STATUS_COL = "STATUS";
    private static final String FURNISHED_COL = "FURNISHED";
    private static final String PHOTOS_COL = "PHOTOS";
    private static final String AVAILABILITY_DATE_COL = "AVAILABILITY_DATE";
    private static final String DESCRIPTION_COL = "DESCRIPTION";


    //Reservations Table Column Names
    private static final String DATE_COL = "DATE";
    private static final String TIME_COL = "TIME";
    private static final String PERIOD_COL = "PERIOD";



    //SQL Statements for Tables Creation
    String SQL_CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + EMAIL_COL + " TEXT  NOT NULL," +
            HASHED_PASSWORD_COL + " TEXT NOT NULL," + FIRSTNAME_COL + " TEXT NOT NULL," + LASTNAME_COL + " TEXT NOT NULL," +
            GENDER_COL + " TEXT NOT NULL," + COUNTRY_COL + " TEXT NOT NULL," + CITY_COL + " TEXT NOT NULL," + PHONE_NUMBER_COL + " TEXT NOT NULL," + NATIONALITY_COL +
            " TEXT NOT NULL," + SALARY_COL + " TEXT NOT NULL," + FAMILY_SIZE_COL + " TEXT NOT NULL," + OCCUPATION_COL + " TEXT NOT NULL)";

    String SQL_CREATE_TABLE_AGENCY_USERS = "CREATE TABLE " + TABLE_AGENCY_USERS + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + EMAIL_COL + " TEXT NOT NULL," +
            HASHED_PASSWORD_COL + " TEXT NOT NULL," + AGENCY_NAME_COL + " TEXT NOT NULL," + COUNTRY_COL + " TEXT NOT NULL," + CITY_COL +
            " TEXT NOT NULL," + PHONE_NUMBER_COL + " TEXT NOT NULL)";


    String SQL_CREATE_TABLE_CARS = "CREATE TABLE " + TABLE_CARS + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + YEAR_COL + " INTEGER NOT NULL," +
            MAKE_COL + " TEXT NOT NULL," + MODEL_COL + " TEXT NOT NULL," + DISTANCE_COL + " TEXT NOT NULL," + PRICE_COL + " INTEGER NOT NULL," +
            ACCIDENTS_COL + " BOOLEAN NOT NULL)";

    String SQL_CREATE_TABLE_HOUSES = "CREATE TABLE " + TABLE_HOUSES + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + AGENCY_NAME_COL + " TEXT NOT NULL,"+ AGENCY_USER_ID_COL + " INTEGAR NOT NULL,"
            + CITY_COL + " TEXT NOT NULL," + POSTAL_ADDRESS_COL + " TEXT NOT NULL," + AREA_COL + " INTEGER NOT NULL," + CONSTRUCTION_COL + " INTEGER NOT NULL," + BEDROOMS_COL + " INTEGER NOT NULL," +
            PRICE_COL + " INTEGER NOT NULL,"+ STATUS_COL + " BOOLEAN NOT NULL,"+ FURNISHED_COL + " BOOLEAN NOT NULL,"+ PHOTOS_COL +
            " TEXT NOT NULL,"+ AVAILABILITY_DATE_COL + " TEXT NOT NULL,"+ DESCRIPTION_COL + " TEXT NOT NULL)";


    String SQL_CREATE_TABLE_RESERVATIONS = "CREATE TABLE " + TABLE_RESERVATIONS + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ID_COL + " INTEGER NOT NULL," +
            HOUSE_ID_COL + " INTEGER NOT NULL," + AGENCY_USER_ID_COL + " INTEGER NOT NULL," +
            DATE_COL + " TEXT NOT NULL," + PERIOD_COL + " INTEGER NOT NULL," + TIME_COL + " TEXT NOT NULL)";

    String SQL_CREATE_TABLE_FAVORITES = "CREATE TABLE " + TABLE_FAVORITES + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ID_COL + " INTEGER NOT NULL,"
            + HOUSE_ID_COL + " INTEGER NOT NULL)";


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
        db.execSQL(SQL_CREATE_TABLE_HOUSES);
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
        if ((result = sqLiteDatabase.insert(TABLE_USERS, null, contentValues)) == -1) {
            sqLiteDatabase.close();
            return false;
        }

        else {
            sqLiteDatabase.close();
            return true;
        }

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
        if ((result = sqLiteDatabase.insert(TABLE_AGENCY_USERS, null, contentValues)) == -1) {
            sqLiteDatabase.close();
            return false;
        }


        else {
            sqLiteDatabase.close();
            return true;
        }

    }

    public User getUserByEmailAddress(String emailAddress) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_USERS, new String[]{ID_COL, EMAIL_COL, HASHED_PASSWORD_COL,
                        FIRSTNAME_COL, LASTNAME_COL, GENDER_COL, COUNTRY_COL, CITY_COL, PHONE_NUMBER_COL, NATIONALITY_COL
                , SALARY_COL, FAMILY_SIZE_COL, OCCUPATION_COL},
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
            user.setNationality(cursor.getString(9));
            user.setSalary(cursor.getString(10));
            user.setFamilySize(cursor.getString(11));
            user.setOccupation(cursor.getString(12));
            cursor.close();
            sqLiteDatabase.close();
            return user;
        }

        cursor.close();
        sqLiteDatabase.close();
        return null;
    }

    public AgencyUser getAgencyUserByEmailAddress(String emailAddress) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_AGENCY_USERS, new String[]{ID_COL, EMAIL_COL, HASHED_PASSWORD_COL,
                        AGENCY_NAME_COL, COUNTRY_COL, CITY_COL, PHONE_NUMBER_COL},
                EMAIL_COL + "=?", new String[]{emailAddress}, null, null, null);

        if (cursor.moveToFirst()) {
            AgencyUser user = new AgencyUser();
            user.setId(cursor.getInt(0));
            user.setEmailAddress(cursor.getString(1));
            user.setHashedPassword(cursor.getString(2));
            user.setAgencyName(cursor.getString(3));
            user.setCountry(cursor.getString(4));
            user.setCity(cursor.getString(5));
            user.setPhoneNumber(cursor.getString(6));

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
        contentValues.put(SALARY_COL, updatedUser.getSalary());
        contentValues.put(FAMILY_SIZE_COL, updatedUser.getFamilySize());
        contentValues.put(OCCUPATION_COL, updatedUser.getOccupation());
        contentValues.put(PHONE_NUMBER_COL, updatedUser.getPhoneNumber());
        int rowsAffected = sqLiteDatabase.update(TABLE_USERS, contentValues, EMAIL_COL + " = ?", new String[]{emailAddress});
        if (rowsAffected == 0)
            return false;
        else
            return true;
    }

    public boolean updateHouse(House house) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BEDROOMS_COL, house.getBedrooms());
        contentValues.put(PRICE_COL, house.getPrice());
        contentValues.put(STATUS_COL, house.isStatus());
        contentValues.put(FURNISHED_COL, house.isFurnished());
        contentValues.put(DESCRIPTION_COL, house.getDescription());
        contentValues.put(AVAILABILITY_DATE_COL, house.getAvailabilityDate());

        int rowsAffected = sqLiteDatabase.update(TABLE_HOUSES, contentValues, ID_COL + " = ?", new String[]{house.getHouseId().toString()});
        if (rowsAffected == 0)
            return false;
        else
            return true;
    }

    public boolean updateAgencyInformation(AgencyUser updatedUser, String emailAddress) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL_COL, updatedUser.getEmailAddress());
        contentValues.put(AGENCY_NAME_COL, updatedUser.getAgencyName());
        contentValues.put(PHONE_NUMBER_COL, updatedUser.getPhoneNumber());
        int rowsAffected = sqLiteDatabase.update(TABLE_AGENCY_USERS, contentValues, EMAIL_COL + " = ?", new String[]{emailAddress});
        if (rowsAffected == 0)
            return false;
        else
            return true;
    }

    public void deleteCustomerById(Integer id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_USERS, ID_COL + "=" + id, null);
    }

    public void deleteHouseById(Integer id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_HOUSES, ID_COL + "=" + id, null);
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

    public boolean addHouse(House house) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CITY_COL, house.getCity());
        contentValues.put(AGENCY_USER_ID_COL, house.getAgencyId());
        contentValues.put(POSTAL_ADDRESS_COL, house.getPostalAddress());
        contentValues.put(AREA_COL, house.getArea());
        contentValues.put(CONSTRUCTION_COL, house.getConstructionYear());
        contentValues.put(BEDROOMS_COL, house.getBedrooms());
        contentValues.put(PRICE_COL, house.getPrice());
        contentValues.put(STATUS_COL, house.isStatus());
        contentValues.put(FURNISHED_COL, house.isFurnished());
        contentValues.put(PHOTOS_COL, house.getPhotos());
        contentValues.put(AVAILABILITY_DATE_COL, house.getAvailabilityDate().toString());
        contentValues.put(DESCRIPTION_COL, house.getDescription());
        contentValues.put(AGENCY_NAME_COL, house.getAgencyName());

        long result;
        if ((result = sqLiteDatabase.insert(TABLE_HOUSES, null, contentValues)) != -1) {
            sqLiteDatabase.close();
            return true;
        }
        else
            return false;
    }

    public House getHouseById(Integer houseId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_HOUSES,new String[]{ID_COL, CITY_COL, POSTAL_ADDRESS_COL, AREA_COL, CONSTRUCTION_COL,
                        BEDROOMS_COL, PRICE_COL,STATUS_COL,FURNISHED_COL,PHOTOS_COL,AVAILABILITY_DATE_COL,DESCRIPTION_COL},
                ID_COL + "=" + houseId, null, null, null ,null);

        if (cursor.moveToFirst()) {
            House house = new House();
            house.setHouseId(cursor.getInt(0));
            house.setCity(cursor.getString(1));
            house.setPostalAddress(cursor.getString(2));
            house.setArea(cursor.getInt(3));
            house.setConstructionYear(cursor.getInt(4));
            house.setBedrooms(cursor.getInt(5));
            house.setPrice(cursor.getInt(6));
            house.setStatus(cursor.getInt(7) == 1);
            house.setFurnished(cursor.getInt(8) == 1);
            house.setPhotos(String.valueOf(cursor.getInt(9)));
            house.setAvailabilityDate(String.valueOf(cursor.getInt(10)));
            house.setDescription(cursor.getString(11));
            return house;
        }

        return null;
    }

    public String getAgencyNameByHouseId(Integer houseId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_HOUSES, new String[]{AGENCY_NAME_COL},
                ID_COL + "=" + houseId, null, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }
        return " ";
    }
    public String getUserNameByUserId(Integer userId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_USERS, new String[]{FIRSTNAME_COL,LASTNAME_COL},
                ID_COL + "=" + userId, null, null, null, null);
        if (cursor.moveToFirst()) {
            String FirstName = cursor.getString(0);
            String LastName = cursor.getString(1);
            return FirstName + " " + LastName;
        }
        return " ";
    }

    public List<String> getAllCities() {
        List<String> allCitiesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_HOUSES, new String[]{CITY_COL},
                null,null, CITY_COL, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                allCitiesList.add(cursor.getString(0));
            }
            return allCitiesList;
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

    public List<House> getAllHouses() {
        List<House> allHousesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_HOUSES, new String[]{ID_COL, CITY_COL, POSTAL_ADDRESS_COL, AREA_COL, CONSTRUCTION_COL,
                        BEDROOMS_COL, PRICE_COL,STATUS_COL,FURNISHED_COL,PHOTOS_COL,AVAILABILITY_DATE_COL,DESCRIPTION_COL,AGENCY_USER_ID_COL},
                null,null, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                House house = new House();
                house.setHouseId(cursor.getInt(0));
                house.setCity(cursor.getString(1));
                house.setPostalAddress(cursor.getString(2));
                house.setArea(cursor.getInt(3));
                house.setConstructionYear(cursor.getInt(4));
                house.setBedrooms(cursor.getInt(5));
                house.setPrice(cursor.getInt(6));
                house.setStatus(cursor.getInt(7) == 1);
                house.setFurnished(cursor.getInt(8) == 1);
                house.setPhotos(String.valueOf(cursor.getInt(9)));
                house.setAvailabilityDate(cursor.getString(10));
                house.setDescription(cursor.getString(11));
                house.setAgencyId(cursor.getInt(12));

                allHousesList.add(house);
                cursor.moveToNext();
            }
            return allHousesList;
        }
        return null;
    }

    public List<House> getAllHousesByAgencyId(int agencyId) {
        List<House> allHousesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(
                    TABLE_HOUSES, new String[]{ID_COL, CITY_COL, POSTAL_ADDRESS_COL, AREA_COL, CONSTRUCTION_COL,
                            BEDROOMS_COL, PRICE_COL, STATUS_COL, FURNISHED_COL, PHOTOS_COL, AVAILABILITY_DATE_COL, DESCRIPTION_COL, AGENCY_USER_ID_COL},
                    AGENCY_USER_ID_COL + "=" + agencyId, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    House house = new House();
                    house.setHouseId(cursor.getInt(0));
                    house.setCity(cursor.getString(1));
                    house.setPostalAddress(cursor.getString(2));
                    house.setArea(cursor.getInt(3));
                    house.setConstructionYear(cursor.getInt(4));
                    house.setBedrooms(cursor.getInt(5));
                    house.setPrice(cursor.getInt(6));
                    house.setStatus(cursor.getInt(7) == 1);
                    house.setFurnished(cursor.getInt(8) == 1);
                    house.setPhotos(String.valueOf(cursor.getInt(9)));
                    house.setAvailabilityDate(cursor.getString(10));
                    house.setDescription(cursor.getString(11));
                    house.setAgencyId(cursor.getInt(12));

                    allHousesList.add(house);
                    cursor.moveToNext();
                }
                return allHousesList;
            }
            return null;
        }
        finally {
            cursor.close();
        }
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

    public List<House> getFavoritesByCustomerId(Integer customerId) {
        List<House> favorites = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_FAVORITES, new String[]{ID_COL, USER_ID_COL, HOUSE_ID_COL}, USER_ID_COL + "=" + customerId, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer houseId = cursor.getInt(2);
                House house = this.getHouseById(houseId);
                Log.d("FAV", house.getCity());
                if (house != null)
                    favorites.add(house);

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

    public boolean addFavoriteHouseToCustomer(Integer customerId, Integer houseId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID_COL, customerId);
        contentValues.put(HOUSE_ID_COL, houseId);
        return sqLiteDatabase.insert(TABLE_FAVORITES, null, contentValues) != -1;
    }

    public void removeFromFavorites(Integer customerId, Integer houseId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_FAVORITES, USER_ID_COL + "=" + customerId + " AND " + HOUSE_ID_COL + "=" + houseId, null);

    }

    public boolean reserveHouseByCustomer(Reservation reservation) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID_COL, reservation.getCustomerId());
        contentValues.put(HOUSE_ID_COL, reservation.getHouseId());
        contentValues.put(DATE_COL, reservation.getDate());
        contentValues.put(TIME_COL, reservation.getTime());
        contentValues.put(PERIOD_COL, reservation.getPeriod());
        contentValues.put(AGENCY_USER_ID_COL, reservation.getAgencyId());


        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(STATUS_COL, true);

        Date date = new Date();
        //This method returns the time in millis
        long timeMilli = date.getTime();
        long newTime = timeMilli + 86400000L * Long.parseLong(reservation.getPeriod());
        Date result = new Date(newTime);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(result);

        contentValues2.put(AVAILABILITY_DATE_COL, strDate);

        sqLiteDatabase.update(TABLE_HOUSES, contentValues2,ID_COL + "= ?", new String[]{reservation.getHouseId().toString()});

        return sqLiteDatabase.insert(TABLE_RESERVATIONS, null, contentValues) != -1;
    }

    public List<Reservation> getReservationsByCustomerId(Integer customerId){
        List<Reservation> reservationList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_RESERVATIONS, new String[]{HOUSE_ID_COL, DATE_COL, TIME_COL, PERIOD_COL},
                USER_ID_COL + "=" + customerId, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer houseId = cursor.getInt(0);
                String date = cursor.getString(1);
                String time = cursor.getString(2);
                String period = cursor.getString(3);

                Reservation reservation = new Reservation();
                reservation.setHouseId(houseId);
                reservation.setDate(date);
                reservation.setTime(time);
                reservation.setPeriod(period);
                reservationList.add(reservation);

                cursor.moveToNext();
            }

            return reservationList;
        }
        return null;
    }

    public List<Reservation> getReservationsByAgencyId(Integer AgencyId){
        List<Reservation> reservationList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_RESERVATIONS, new String[]{HOUSE_ID_COL, DATE_COL, TIME_COL, PERIOD_COL,USER_ID_COL},
                AGENCY_USER_ID_COL + "=" + AgencyId, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer houseId = cursor.getInt(0);
                String date = cursor.getString(1);
                String time = cursor.getString(2);
                String period = cursor.getString(3);
                Integer customerId = cursor.getInt(4);

                Reservation reservation = new Reservation();
                reservation.setHouseId(houseId);
                reservation.setDate(date);
                reservation.setTime(time);
                reservation.setPeriod(period);
                reservation.setCustomerId(customerId);
                reservationList.add(reservation);

                cursor.moveToNext();
            }

            return reservationList;
        }
        return null;
    }
}
