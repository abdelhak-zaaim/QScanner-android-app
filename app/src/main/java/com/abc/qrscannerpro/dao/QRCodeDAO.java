package com.abc.qrscannerpro.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.abc.qrscannerpro.db.QRCodeItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QRCodeDAO extends BaseDaoImpl<QRCodeItem, Integer> {

    private List<QRCodeItem> contactCallRecordList = new ArrayList<>();

    public QRCodeDAO(ConnectionSource connectionSource, Class<QRCodeItem> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<QRCodeItem> getAllItems() throws SQLException {
        return this.queryForAll();
    }

    public QRCodeItem getItem(Integer id) throws SQLException {
        return this.queryForId(id);
    }

    public void deleteItem(int id) throws SQLException {
        DeleteBuilder<QRCodeItem, Integer> deleteBuilder = this.deleteBuilder();
        deleteBuilder.where().eq(QRCodeItem.NAME_FIELD_ID, id);
        deleteBuilder.delete();
    }

    public  List<QRCodeItem> getLastItem() throws SQLException {
        QueryBuilder<QRCodeItem, Integer> queryBuilder = queryBuilder();
        queryBuilder.limit(1);
        queryBuilder.orderBy(QRCodeItem.NAME_FIELD_ID, false);
        PreparedQuery<QRCodeItem> preparedQuery = queryBuilder.prepare();
        List<QRCodeItem> itemList = query(preparedQuery);
        return itemList;
    }
}