package com.gas.zhihu.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.gas.zhihu.bean.MapBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "mapbean".
*/
public class MapBeanDao extends AbstractDao<MapBean, Long> {

    public static final String TABLENAME = "mapbean";

    /**
     * Properties of entity MapBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PathName = new Property(1, String.class, "pathName", false, "PATH_NAME");
        public final static Property LocationInfo = new Property(2, String.class, "locationInfo", false, "LOCATION_INFO");
        public final static Property MapName = new Property(3, String.class, "mapName", false, "MAP_NAME");
        public final static Property KeyName = new Property(4, String.class, "keyName", false, "KEY_NAME");
        public final static Property Note = new Property(5, String.class, "note", false, "NOTE");
        public final static Property Longitude = new Property(6, double.class, "longitude", false, "LONGITUDE");
        public final static Property Latitude = new Property(7, double.class, "latitude", false, "LATITUDE");
    }


    public MapBeanDao(DaoConfig config) {
        super(config);
    }
    
    public MapBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"mapbean\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PATH_NAME\" TEXT," + // 1: pathName
                "\"LOCATION_INFO\" TEXT," + // 2: locationInfo
                "\"MAP_NAME\" TEXT," + // 3: mapName
                "\"KEY_NAME\" TEXT UNIQUE ," + // 4: keyName
                "\"NOTE\" TEXT," + // 5: note
                "\"LONGITUDE\" REAL NOT NULL ," + // 6: longitude
                "\"LATITUDE\" REAL NOT NULL );"); // 7: latitude
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"mapbean\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MapBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String pathName = entity.getPathName();
        if (pathName != null) {
            stmt.bindString(2, pathName);
        }
 
        String locationInfo = entity.getLocationInfo();
        if (locationInfo != null) {
            stmt.bindString(3, locationInfo);
        }
 
        String mapName = entity.getMapName();
        if (mapName != null) {
            stmt.bindString(4, mapName);
        }
 
        String keyName = entity.getKeyName();
        if (keyName != null) {
            stmt.bindString(5, keyName);
        }
 
        String note = entity.getNote();
        if (note != null) {
            stmt.bindString(6, note);
        }
        stmt.bindDouble(7, entity.getLongitude());
        stmt.bindDouble(8, entity.getLatitude());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MapBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String pathName = entity.getPathName();
        if (pathName != null) {
            stmt.bindString(2, pathName);
        }
 
        String locationInfo = entity.getLocationInfo();
        if (locationInfo != null) {
            stmt.bindString(3, locationInfo);
        }
 
        String mapName = entity.getMapName();
        if (mapName != null) {
            stmt.bindString(4, mapName);
        }
 
        String keyName = entity.getKeyName();
        if (keyName != null) {
            stmt.bindString(5, keyName);
        }
 
        String note = entity.getNote();
        if (note != null) {
            stmt.bindString(6, note);
        }
        stmt.bindDouble(7, entity.getLongitude());
        stmt.bindDouble(8, entity.getLatitude());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MapBean readEntity(Cursor cursor, int offset) {
        MapBean entity = new MapBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // pathName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // locationInfo
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mapName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // keyName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // note
            cursor.getDouble(offset + 6), // longitude
            cursor.getDouble(offset + 7) // latitude
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MapBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPathName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLocationInfo(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMapName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setKeyName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNote(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLongitude(cursor.getDouble(offset + 6));
        entity.setLatitude(cursor.getDouble(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MapBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MapBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MapBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
