package db.DAO;

import DTO.logs.LoggingPostRequest;
import db.ConnectionBuilder;
import db.DAO.queries.LogQueries;
import entity.LogEntity;
import exceptions.DAOException;
import logging.LogLevel;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class LogDAO {

    private final ConnectionBuilder connectionBuilder;

    public List<LogEntity> getAll() throws DAOException {
        List<LogEntity> result = new ArrayList<>();
        try (Connection conn = connectionBuilder.getConnection();
             PreparedStatement stmt = conn.prepareStatement(LogQueries.GET_ALL.getQuery())) {

            ResultSet rs = stmt.executeQuery();

            mapLogEntity(rs, result);

            rs.close();
        } catch (SQLException e) {
            throw new DAOException("Exception getting logs.", e);
        }
        return result;
    }

//    public List<LogEntity> getByParams(LoggingPostRequest request) throws DAOException {
//        List<LogEntity> result = new ArrayList<>();
//        try (Connection conn = connectionBuilder.getConnection();
//            PreparedStatement stmt = conn.prepareStatement(LogQueries.GET_BY_PARAMS.getQuery())) {
//
//            stmt.setByte(1, request.getLogLevel());
//            stmt.setByte(2, request.getLogLevel());
//            stmt.setString(3, request.getLogMessage());
//            stmt.setString(4, request.getLogMessage());
//            stmt.setString(5, request.getLogError());
//            stmt.setString(6, request.getLogError());
//
//            ResultSet rs = stmt.executeQuery();
//
//            mapLogEntity(rs, result);
//
//            rs.close();
//        } catch (SQLException e) {
//            throw new DAOException("Exception getting logs by params.", e);
//        }
//        return result;
//    }

    public boolean createNew(LogEntity entity) throws DAOException {
        try (Connection conn = connectionBuilder.getConnection();
            PreparedStatement stmt = conn.prepareStatement(LogQueries.CREATE_NEW.getQuery())) {

            stmt.setString(1, entity.getLogMessage());
            stmt.setString(2, entity.getLogError());
            stmt.setByte(3, entity.getLevel().getLevel());

            if (stmt.executeUpdate() == 0) {
                throw new SQLException("New log wasn't created.");
            }

            return true;
        } catch (SQLException e) {
            throw new DAOException("Exception inserting log.", e);
        }
    }

    private void mapLogEntity(ResultSet rs, List<LogEntity> resultList) throws SQLException {
        while(rs.next()) {
            LogEntity logEntity = new LogEntity(rs.getLong("log_id"),
                    rs.getString("log_message"),
                    rs.getString("log_error"),
                    LogLevel.getByCode(rs.getByte("log_level")),
                    rs.getTimestamp("created_at") != null ?
                            new Date(rs.getTimestamp("created_at").getTime()) : null);
            resultList.add(logEntity);
        }
    }

}
