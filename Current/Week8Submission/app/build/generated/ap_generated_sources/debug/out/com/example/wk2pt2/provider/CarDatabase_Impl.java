package com.example.wk2pt2.provider;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CarDatabase_Impl extends CarDatabase {
  private volatile CarDao _carDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `cars` (`carId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `carMaker` TEXT, `carModel` TEXT, `carYear` INTEGER NOT NULL, `carColor` TEXT, `carSeats` INTEGER NOT NULL, `carPrice` REAL NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'cfbc5bb2b275b9ec4948fd573a191b93')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `cars`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCars = new HashMap<String, TableInfo.Column>(7);
        _columnsCars.put("carId", new TableInfo.Column("carId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("carMaker", new TableInfo.Column("carMaker", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("carModel", new TableInfo.Column("carModel", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("carYear", new TableInfo.Column("carYear", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("carColor", new TableInfo.Column("carColor", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("carSeats", new TableInfo.Column("carSeats", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCars.put("carPrice", new TableInfo.Column("carPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCars = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCars = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCars = new TableInfo("cars", _columnsCars, _foreignKeysCars, _indicesCars);
        final TableInfo _existingCars = TableInfo.read(_db, "cars");
        if (! _infoCars.equals(_existingCars)) {
          return new RoomOpenHelper.ValidationResult(false, "cars(com.example.wk2pt2.provider.Car).\n"
                  + " Expected:\n" + _infoCars + "\n"
                  + " Found:\n" + _existingCars);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "cfbc5bb2b275b9ec4948fd573a191b93", "8e036a9de178441a1a24e436159f6eaf");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cars");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `cars`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public CarDao carDao() {
    if (_carDao != null) {
      return _carDao;
    } else {
      synchronized(this) {
        if(_carDao == null) {
          _carDao = new CarDao_Impl(this);
        }
        return _carDao;
      }
    }
  }
}
