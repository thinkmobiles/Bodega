package com.bodega.greendao_generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.thinkmobiles.bodega.db.daogen");
        addEntities(schema);
        new DaoGenerator().generateAll(schema, "app/src/main/java/");
    }

    private static void addEntities(Schema schema) {
        Entity itemEntity = schema.addEntity("OrderItem");
        itemEntity.addIdProperty().autoincrement();
        itemEntity.addStringProperty("name").notNull();
        itemEntity.addStringProperty("pdf");
        itemEntity.addStringProperty("icon");
        Property customerProperty = itemEntity.addLongProperty("customerId").notNull().getProperty();

        Entity customerEntity = schema.addEntity("Customer");
        customerEntity.addIdProperty().autoincrement();
        customerEntity.addStringProperty("name").notNull().unique();
        customerEntity.addToMany(itemEntity, customerProperty);
    }
}
