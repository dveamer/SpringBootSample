package io.dveamer.sample.player


class PlayerSql {

    public static final String newSeq = """
		SELECT MAX(id) + 1 FROM player
	""";

    public static final String select = """
		SELECT id, player_name
		FROM player
		WHERE 1=1
	""";

    public static final String conditionId = """
		AND id = :id
	""";

    public static final String conditionInId = """
		AND id IN ( :idSet )
	""";

    public static final String insert = """
		INSERT INTO player
		( id
		, player_name
		/*INSERT_COLUMNS*/
		) VALUES (
		  :id
		, :playerName
		/*INSERT_VALUES*/
		)
	""";

    public static final String update = """
		UPDATE player
		SET player_name = :playerName
            /*UPDATE_VALUES*/
        WHERE 1=1
        AND id = :id
	""";
}
