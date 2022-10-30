package db.DAO;

import db.ConnectionBuilder;
import db.DAO.queries.ClanQueries;
import entity.Clan;
import entity.Player;
import exceptions.DAOException;
import lombok.AllArgsConstructor;

import java.sql.*;

@AllArgsConstructor
public class ClanDAO {

    private final ConnectionBuilder connectionBuilder;

    public Clan getClanById(long id) throws DAOException {
        Clan result = null;
        try (Connection conn = connectionBuilder.getConnection();
            PreparedStatement stmt = conn.prepareStatement(ClanQueries.GET_BY_ID.getQuery())) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                if (result == null) {
                    result = new Clan(rs.getLong("clan_id"),
                            rs.getString("clan_name"),
                            rs.getLong("gold"));
                }
                result.getMembers().add(
                        new Player(rs.getLong("player_id"),
                                rs.getLong("player_gold"),
                                rs.getString("player_username")));
            }

            rs.close();

        } catch (SQLException e) {
            throw new DAOException("Exception getting clan.", e);
        }
        return result;
    }

    public Clan registerNewClan(String clanName) throws DAOException {
        Clan result = null;
        try (Connection conn = connectionBuilder.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ClanQueries.CREATE_NEW.getQuery(),
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, clanName);
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next() && generatedKeys != null) {
                result = new Clan(generatedKeys.getLong(1), clanName, 0L);
            } else {
                conn.rollback();
                throw new DAOException("New clan was not inserted", null);
            }

            generatedKeys.close();
        } catch (SQLException e) {
            throw new DAOException("Exception registering new clan", e);
        }

        return result;
    }

    public boolean changeGoldAmount(long clanId, long goldAmount) throws DAOException {
        try (Connection conn = connectionBuilder.getConnection();
            PreparedStatement stmt = conn.prepareStatement(ClanQueries.CHANGE_GOLD.getQuery())) {

            stmt.setLong(1, goldAmount);
            stmt.setLong(2, clanId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 1) {
                conn.rollback();
                throw new SQLException("More than one row was updated. Rolled back.");
            } else if (rowsUpdated == 0) {
                throw new SQLException("No clan found with given id: " + clanId);
            }

            return true;
        } catch (SQLException e) {
            throw new DAOException("Exception changing gold amount.", e);
        }
    }

}
