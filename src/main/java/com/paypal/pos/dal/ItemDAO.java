package com.paypal.pos.dal;

import com.paypal.pos.model.Item;
import com.paypal.pos.exception.*;

import java.io.Serializable;
import java.util.List;

/**
 * User: pderoxas
 * Date: 2/24/14
 * Time: 9:48 AM
 * DAO interface for the Items
 */
public interface ItemDAO<T extends Item, K extends Serializable> {

    /**
     * Return a List of all instances of a given resource type
     * @return List of Resource Objects
     */
    Iterable<T> getList() throws DalException;

    /**
     * Return a specific resource object by id
     * @param id - The ID of the resource
     * @return Single Resource Object
     */
    T getById(K id) throws DalException;

    /**
     * Adds a new instance of a resource
     * @param resource - The resource to add
     * @return primary key of the resource
     * @throws DalException
     */
    K addResource(T resource) throws DalException;

    /**
     * Updates an existing instance of a resource
     * @param resource - The resource to update
     * @return primary key of the resource
     * @throws DalException
     */
    K updateResource(T resource) throws DalException;

    /**
     * Deletes an existing instance of a resource
     * @param id - The ID of the resource
     * @throws DalException
     */
    void deleteResource(K id) throws DalException;
}
