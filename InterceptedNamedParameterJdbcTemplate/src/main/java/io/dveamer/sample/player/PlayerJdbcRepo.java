package io.dveamer.sample.Player;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class PlayerJdbcRepo implements PlayerRepo {

    private final NamedParameterJdbcOperations npJdbcTemplate;

    public PlayerJdbcRepo(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.npJdbcTemplate = namedParameterJdbcOperations;
    }

    @Override
    public long newSeq() {
        return npJdbcTemplate.queryForObject(PlayerSql.newSeq, new MapSqlParameterSource(), Long.class);
    }

    @Override
    public Player findById(long id) {

        String sql = new StringBuffer(PlayerSql.select)
                .append(PlayerSql.conditionId).toString();

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        return npJdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(Player.class));
    }

    @Override
    public void insert(Player player) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", player.getId());
        namedParameters.addValue("playerName", player.getPlayerName());
        npJdbcTemplate.update(PlayerSql.insert, namedParameters);
    }

}
