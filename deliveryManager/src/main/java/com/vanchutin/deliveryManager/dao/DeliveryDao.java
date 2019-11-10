package com.vanchutin.deliveryManager.dao;

import com.vanchutin.annotation.ResourceSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public class DeliveryDao {

    @ResourceSql("createDelivery.sql")
    private String createDeliveryTemplate;

    @ResourceSql("getOrderByDroneId.sql")
    private String getOrderByDroneIdTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public void createDelivery(int droneId, int orderId){
        MapSqlParameterSource parameters  = new MapSqlParameterSource();
        parameters.addValue("droneId", droneId);
        parameters.addValue("orderId", orderId);
        namedJdbcTemplate.update(createDeliveryTemplate, parameters);
    }

    public int getOrderByDroneId(int droneId){
        MapSqlParameterSource parameters  = new MapSqlParameterSource();
        parameters.addValue("droneId", droneId);
        return namedJdbcTemplate.queryForObject(createDeliveryTemplate, parameters, Integer.class);
    }

}