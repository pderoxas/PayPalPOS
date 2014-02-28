package com.paypal.pos.dal;

import com.paypal.pos.PayPalPos;
import com.paypal.pos.exception.DalException;
import com.paypal.pos.model.Item;

import java.io.Serializable;

/**
 * Created by pderoxas on 2/28/14.
 */
public class InStoreItemDAO implements ItemDAO {
    @Override
    public Iterable getList() throws DalException {
        return null;
    }

    @Override
    public Item getById(Serializable id) throws DalException {

        //Temporary for now - just get from Map
        //Eventually retrieve from database
        return PayPalPos.inventoryMap.get(id);
    }

    @Override
    public Serializable addResource(Item resource) throws DalException {
        return null;
    }

    @Override
    public Serializable updateResource(Item resource) throws DalException {
        return null;
    }

    @Override
    public void deleteResource(Serializable id) throws DalException {

    }
}
