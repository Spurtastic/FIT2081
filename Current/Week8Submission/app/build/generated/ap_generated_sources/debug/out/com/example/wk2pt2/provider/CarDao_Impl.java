package com.example.wk2pt2.provider;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CarDao_Impl implements CarDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Car> __insertionAdapterOfCar;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLastCar;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllCars;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldCars;

  public CarDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCar = new EntityInsertionAdapter<Car>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `cars` (`carId`,`carMaker`,`carModel`,`carYear`,`carColor`,`carSeats`,`carPrice`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Car value) {
        stmt.bindLong(1, value.getId());
        if (value.getMaker() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMaker());
        }
        if (value.getModel() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getModel());
        }
        stmt.bindLong(4, value.getYear());
        if (value.getColor() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getColor());
        }
        stmt.bindLong(6, value.getSeats());
        stmt.bindDouble(7, value.getPrice());
      }
    };
    this.__preparedStmtOfDeleteLastCar = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from cars where carId = (Select max(carId) from cars)";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllCars = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from cars";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldCars = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from cars where carYear < ?";
        return _query;
      }
    };
  }

  @Override
  public void addCar(final Car car) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCar.insert(car);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteLastCar() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLastCar.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteLastCar.release(_stmt);
    }
  }

  @Override
  public void deleteAllCars() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllCars.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllCars.release(_stmt);
    }
  }

  @Override
  public void deleteOldCars(final int year) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldCars.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, year);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteOldCars.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Car>> getAllCars() {
    final String _sql = "Select * from cars";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"cars"}, false, new Callable<List<Car>>() {
      @Override
      public List<Car> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "carId");
          final int _cursorIndexOfMaker = CursorUtil.getColumnIndexOrThrow(_cursor, "carMaker");
          final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "carModel");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "carYear");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "carColor");
          final int _cursorIndexOfSeats = CursorUtil.getColumnIndexOrThrow(_cursor, "carSeats");
          final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "carPrice");
          final List<Car> _result = new ArrayList<Car>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Car _item;
            final String _tmpMaker;
            _tmpMaker = _cursor.getString(_cursorIndexOfMaker);
            final String _tmpModel;
            _tmpModel = _cursor.getString(_cursorIndexOfModel);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final String _tmpColor;
            _tmpColor = _cursor.getString(_cursorIndexOfColor);
            final int _tmpSeats;
            _tmpSeats = _cursor.getInt(_cursorIndexOfSeats);
            final double _tmpPrice;
            _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
            _item = new Car(_tmpMaker,_tmpModel,_tmpYear,_tmpColor,_tmpSeats,_tmpPrice);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<Car> getCarMaker(final String name) {
    final String _sql = "Select * from cars where carMaker=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "carId");
      final int _cursorIndexOfMaker = CursorUtil.getColumnIndexOrThrow(_cursor, "carMaker");
      final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "carModel");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "carYear");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "carColor");
      final int _cursorIndexOfSeats = CursorUtil.getColumnIndexOrThrow(_cursor, "carSeats");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "carPrice");
      final List<Car> _result = new ArrayList<Car>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Car _item;
        final String _tmpMaker;
        _tmpMaker = _cursor.getString(_cursorIndexOfMaker);
        final String _tmpModel;
        _tmpModel = _cursor.getString(_cursorIndexOfModel);
        final int _tmpYear;
        _tmpYear = _cursor.getInt(_cursorIndexOfYear);
        final String _tmpColor;
        _tmpColor = _cursor.getString(_cursorIndexOfColor);
        final int _tmpSeats;
        _tmpSeats = _cursor.getInt(_cursorIndexOfSeats);
        final double _tmpPrice;
        _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
        _item = new Car(_tmpMaker,_tmpModel,_tmpYear,_tmpColor,_tmpSeats,_tmpPrice);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Car> getCarModel(final String name) {
    final String _sql = "Select * from cars where carModel=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "carId");
      final int _cursorIndexOfMaker = CursorUtil.getColumnIndexOrThrow(_cursor, "carMaker");
      final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "carModel");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "carYear");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "carColor");
      final int _cursorIndexOfSeats = CursorUtil.getColumnIndexOrThrow(_cursor, "carSeats");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "carPrice");
      final List<Car> _result = new ArrayList<Car>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Car _item;
        final String _tmpMaker;
        _tmpMaker = _cursor.getString(_cursorIndexOfMaker);
        final String _tmpModel;
        _tmpModel = _cursor.getString(_cursorIndexOfModel);
        final int _tmpYear;
        _tmpYear = _cursor.getInt(_cursorIndexOfYear);
        final String _tmpColor;
        _tmpColor = _cursor.getString(_cursorIndexOfColor);
        final int _tmpSeats;
        _tmpSeats = _cursor.getInt(_cursorIndexOfSeats);
        final double _tmpPrice;
        _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
        _item = new Car(_tmpMaker,_tmpModel,_tmpYear,_tmpColor,_tmpSeats,_tmpPrice);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Car> getCarYear(final String name) {
    final String _sql = "Select * from cars where carYear=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "carId");
      final int _cursorIndexOfMaker = CursorUtil.getColumnIndexOrThrow(_cursor, "carMaker");
      final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "carModel");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "carYear");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "carColor");
      final int _cursorIndexOfSeats = CursorUtil.getColumnIndexOrThrow(_cursor, "carSeats");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "carPrice");
      final List<Car> _result = new ArrayList<Car>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Car _item;
        final String _tmpMaker;
        _tmpMaker = _cursor.getString(_cursorIndexOfMaker);
        final String _tmpModel;
        _tmpModel = _cursor.getString(_cursorIndexOfModel);
        final int _tmpYear;
        _tmpYear = _cursor.getInt(_cursorIndexOfYear);
        final String _tmpColor;
        _tmpColor = _cursor.getString(_cursorIndexOfColor);
        final int _tmpSeats;
        _tmpSeats = _cursor.getInt(_cursorIndexOfSeats);
        final double _tmpPrice;
        _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
        _item = new Car(_tmpMaker,_tmpModel,_tmpYear,_tmpColor,_tmpSeats,_tmpPrice);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Car> getCarSeats(final String name) {
    final String _sql = "Select * from cars where carSeats=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "carId");
      final int _cursorIndexOfMaker = CursorUtil.getColumnIndexOrThrow(_cursor, "carMaker");
      final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "carModel");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "carYear");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "carColor");
      final int _cursorIndexOfSeats = CursorUtil.getColumnIndexOrThrow(_cursor, "carSeats");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "carPrice");
      final List<Car> _result = new ArrayList<Car>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Car _item;
        final String _tmpMaker;
        _tmpMaker = _cursor.getString(_cursorIndexOfMaker);
        final String _tmpModel;
        _tmpModel = _cursor.getString(_cursorIndexOfModel);
        final int _tmpYear;
        _tmpYear = _cursor.getInt(_cursorIndexOfYear);
        final String _tmpColor;
        _tmpColor = _cursor.getString(_cursorIndexOfColor);
        final int _tmpSeats;
        _tmpSeats = _cursor.getInt(_cursorIndexOfSeats);
        final double _tmpPrice;
        _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
        _item = new Car(_tmpMaker,_tmpModel,_tmpYear,_tmpColor,_tmpSeats,_tmpPrice);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Car> getCarColor(final String name) {
    final String _sql = "Select * from cars where carColor=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "carId");
      final int _cursorIndexOfMaker = CursorUtil.getColumnIndexOrThrow(_cursor, "carMaker");
      final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "carModel");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "carYear");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "carColor");
      final int _cursorIndexOfSeats = CursorUtil.getColumnIndexOrThrow(_cursor, "carSeats");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "carPrice");
      final List<Car> _result = new ArrayList<Car>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Car _item;
        final String _tmpMaker;
        _tmpMaker = _cursor.getString(_cursorIndexOfMaker);
        final String _tmpModel;
        _tmpModel = _cursor.getString(_cursorIndexOfModel);
        final int _tmpYear;
        _tmpYear = _cursor.getInt(_cursorIndexOfYear);
        final String _tmpColor;
        _tmpColor = _cursor.getString(_cursorIndexOfColor);
        final int _tmpSeats;
        _tmpSeats = _cursor.getInt(_cursorIndexOfSeats);
        final double _tmpPrice;
        _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
        _item = new Car(_tmpMaker,_tmpModel,_tmpYear,_tmpColor,_tmpSeats,_tmpPrice);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Car> getCarPrice(final String name) {
    final String _sql = "Select * from cars where carPrice=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "carId");
      final int _cursorIndexOfMaker = CursorUtil.getColumnIndexOrThrow(_cursor, "carMaker");
      final int _cursorIndexOfModel = CursorUtil.getColumnIndexOrThrow(_cursor, "carModel");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "carYear");
      final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "carColor");
      final int _cursorIndexOfSeats = CursorUtil.getColumnIndexOrThrow(_cursor, "carSeats");
      final int _cursorIndexOfPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "carPrice");
      final List<Car> _result = new ArrayList<Car>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Car _item;
        final String _tmpMaker;
        _tmpMaker = _cursor.getString(_cursorIndexOfMaker);
        final String _tmpModel;
        _tmpModel = _cursor.getString(_cursorIndexOfModel);
        final int _tmpYear;
        _tmpYear = _cursor.getInt(_cursorIndexOfYear);
        final String _tmpColor;
        _tmpColor = _cursor.getString(_cursorIndexOfColor);
        final int _tmpSeats;
        _tmpSeats = _cursor.getInt(_cursorIndexOfSeats);
        final double _tmpPrice;
        _tmpPrice = _cursor.getDouble(_cursorIndexOfPrice);
        _item = new Car(_tmpMaker,_tmpModel,_tmpYear,_tmpColor,_tmpSeats,_tmpPrice);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
