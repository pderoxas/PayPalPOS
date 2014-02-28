package com.paypal.pos.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 2/24/14
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Item<K> {
    public K getId();
    public String getDescription();
    public Currency getCurrency();
    public BigDecimal getUnitPrice();
    public void setUnitPrice(BigDecimal unitPrice);
}
