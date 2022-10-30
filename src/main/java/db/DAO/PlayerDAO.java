package db.DAO;

import db.ConnectionBuilder;
import db.DAO.queries.PlayerQueries;
import entity.Clan;
import entity.Player;
import exceptions.DAOException;
import lombok.AllArgsConstructor;

import java.sql.*;

@AllArgsConstructor
public class PlayerDAO {

    private final ConnectionBuilder connectionBuilder;

    public Player getPlayerById(long id) throws DAOException {
        Player result = null;
        try (Connection conn = connectionBuilder.getConnection();
            PreparedStatement stmt = conn.prepareStatement(PlayerQueries.GET_BY_ID.getQuery())) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            result = new Player(rs.getLong("player_id"),
                    rs.getLong("gold"),
                    rs.getString("player_username"));
            result.setClan(new Clan(rs.getLong("clan_id"),
                    rs.getString("clan_name"),
                    rs.getLong("clan_gold")));

            if (rs.next()) {
                throw new SQLException("Fetch more than one row for unique players.");
            }

            rs.close();

        } catch (SQLException e) {
            throw new DAOException("Exception getting player.", e);
        }
        return result;
    }

    public Player registerNewPlayer(String username) throws DAOException {
        Player result = null;
        try (Connection conn = connectionBuilder.getConnection();
             PreparedStatement stmt = conn.prepareStatement(PlayerQueries.CREATE_NEW.getQuery(),
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username);
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next() && generatedKeys != null) {
                result = new Player(generatedKeys.getLong(1), 0L, username);
            } else {
                conn.rollback();
                throw new SQLException("New player not created.");
            }

            generatedKeys.close();
        } catch (SQLException e) {
            throw new DAOException("Exception creating new player", e);
        }

        return result;
    }

    public boolean changeGoldAmount(long playerId, long goldAmount) throws DAOException {
        try (Connection conn = connectionBuilder.getConnection();
            PreparedStatement stmt = conn.prepareStatement(PlayerQueries.CHANGE_GOLD.getQuery())) {

            stmt.setLong(1, goldAmount);
            stmt.setLong(2, playerId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 1) {
                conn.rollback();
                throw new SQLException("More than one row updated.");
            } else if (rowsUpdated == 0) {
                throw new SQLException("No user found with given id: " + playerId);
            }

            return true;
        } catch (SQLException e) {
            throw new DAOException("Exception changing gold amount.", e);
        }
    }

}
