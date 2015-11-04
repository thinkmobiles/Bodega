package com.thinkmobiles.bodega.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.api.ProductWrapper;
import com.thinkmobiles.bodega.db.daogen.Customer;
import com.thinkmobiles.bodega.db.daogen.CustomerDao;
import com.thinkmobiles.bodega.db.daogen.DaoMaster;
import com.thinkmobiles.bodega.db.daogen.DaoSession;
import com.thinkmobiles.bodega.db.daogen.OrderItem;
import com.thinkmobiles.bodega.db.daogen.OrderItemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 28.10.15.
 */
public class DBManager {

    private static DBManager dbManager;

    private Context mContext;
    private DaoSession mDaoSession;

    private static final String BODEGA_DB = "bodega_db";

    public DBManager(Context context) {
        mContext = context.getApplicationContext();
        init();
    }

    public static synchronized DBManager getInstance(Context context) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }
        return dbManager;
    }

    public void init() {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(mContext, BODEGA_DB, null, DaoMaster.SCHEMA_VERSION);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public List<OrderItem> getOrders() {
        return mDaoSession.getOrderItemDao().queryBuilder().list();
    }

    public List<OrderItem> addOrder(ItemWrapper itemWrapper, Customer customer) {
        List<OrderItem> result = customer.getOrderItemList();
        OrderItemDao orderItemDao = mDaoSession.getOrderItemDao();
        OrderItem orderItem = new OrderItem();
        orderItem.setName(itemWrapper.getName());
        orderItem.setIcon(itemWrapper.getIcon());
        orderItem.setPdf(itemWrapper.getPdf());
        orderItem.setCustomerId(customer.getId());
        orderItemDao.insert(orderItem);
        result.add(orderItem);
        return result;
    }

    // GreenDAO compares objects by fields, not by links, so it's ok to remove items such way
    public List<OrderItem> deleteOrder(OrderItem orderItem) {
        List<OrderItem> result = getCustomerById(orderItem.getCustomerId()).getOrderItemList();
        OrderItemDao orderItemDao = mDaoSession.getOrderItemDao();
        orderItemDao.delete(orderItem);
        result.remove(orderItem);
        return result;
    }

    private Customer getCustomerById(long customerId) {
        return mDaoSession.getCustomerDao()
                .queryBuilder()
                .where(CustomerDao.Properties.Id.eq(customerId))
                .list().get(0);
    }

    public void deleteAllOrders() {
        mDaoSession.getOrderItemDao().deleteAll();
    }

    public List<Customer> getCustomers() {
        CustomerDao customerDao = mDaoSession.getCustomerDao();
        return customerDao.queryBuilder().orderAsc(CustomerDao.Properties.Name).list();
    }

    public List<String> getCustomerNames() {
        List<String> result = new ArrayList<>();
        for (Customer customer : getCustomers())
            result.add(customer.getName());
        return result;
    }

    public Customer addCustomer(String name) {
        Customer customer = new Customer();
        customer.setName(name);
        CustomerDao customerDao = mDaoSession.getCustomerDao();
        List<Customer> customerList = customerDao.queryBuilder().where(CustomerDao.Properties.Name.eq(name)).list();
        if (customerList.size() > 0)
            return customerList.get(0);
        customerDao.insert(customer);
        return customer;
    }

    public void deleteCustomer(Customer customer) {
        mDaoSession.getOrderItemDao()
                .queryBuilder()
                .where(OrderItemDao.Properties.CustomerId.eq(customer.getId()))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
        mDaoSession.getCustomerDao().delete(customer);
    }

    public void deleteAllCustomers() {
        // Don't need orders without related customers
        deleteAllOrders();
        mDaoSession.getCustomerDao().deleteAll();
    }
}
