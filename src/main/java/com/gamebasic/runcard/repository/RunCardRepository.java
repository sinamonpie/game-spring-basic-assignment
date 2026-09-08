package com.gamebasic.runcard.repository;

import com.gamebasic.runcard.dto.DeckCount;
import com.gamebasic.game.entity.Game;
import com.gamebasic.runcard.entity.RunCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RunCardRepository extends JpaRepository<RunCard, Long> {
    List<RunCard> findAllByGameOrderByIdAsc(Game game);

    void deleteAllByGame(Game game);

    // TODO (Lv 11): @Query 작성
    @Query("SELECT new com.gamebasic.runcard.dto.DeckCount(rc.game, COUNT(rc))" +
             "FROM RunCard rc " +
             "WHERE rc.game IN :games " +
            "GROUP BY rc.game")
    List<DeckCount> countByGames(@Param("games") List<Game> games);
}
