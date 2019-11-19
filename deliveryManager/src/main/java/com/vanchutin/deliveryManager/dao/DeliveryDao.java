package com.vanchutin.deliveryManager.dao;

import com.vanchutin.annotation.ResourceSql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Slf4j
public class DeliveryDao {

    @ResourceSql("createDelivery.sql")
    private String createDeliveryTemplate;

    @ResourceSql("getOrderByDroneId.sql")
    private String getOrderByDroneIdTemplate;

    @ResourceSql("getDroneByOrder.sql")
    private String getDroneIdByOrderTemplate;

    @ResourceSql("deleteDelivery.sql")
    private String deleteDeliveryTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public void createDelivery(int droneId, String orderId) throws DaoLayerException {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("droneId", droneId);
            parameters.addValue("orderId", orderId);
            namedJdbcTemplate.update(createDeliveryTemplate, parameters);
        }catch (DataAccessException e){
            throw new DaoLayerException("Failed to create new delivery");
        }
    }

    public String getOrderByDroneId(int droneId) throws DaoLayerException {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("droneId", droneId);
            return namedJdbcTemplate.queryForObject(getOrderByDroneIdTemplate, parameters, String.class);
        }catch (DataAccessException e){
            throw new DaoLayerException("Failed to fetch orderId from DB", e);
        }
    }

    public Optional<Integer> getDroneByOrderId(String orderId) throws DaoLayerException {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("orderId", orderId);
            Integer droneId = namedJdbcTemplate.queryForObject(getDroneIdByOrderTemplate, parameters, Integer.class);
            return Optional.ofNullable(droneId);
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        } catch (DataAccessException e){
            throw new DaoLayerException(String.format("Problem fetching droneId for order %s", orderId), e);
        }

    }

    public void deleteDelivery(int droneId) throws DaoLayerException {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("droneId", droneId);
            namedJdbcTemplate.update(deleteDeliveryTemplate, parameters);
        }catch (DataAccessException e){
            throw new DaoLayerException(String.format("Failed to delete delivery for drone %d", droneId), e);
        }
    }

}
