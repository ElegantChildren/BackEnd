package elegant.children.catchculture.repository.interaction;

import elegant.children.catchculture.entity.interaction.Interaction;
import elegant.children.catchculture.entity.interaction.LikeStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Integer> {

    @Query("select i from Interaction as i where i.user.id = :userId and i.culturalEvent.id = :culturalEventId and i.likeStar = :likeStar")
    Optional<Interaction> findByUserIdAndCulturalEventIdAndLikeStar(final int userId, final int culturalEventId, final LikeStar likeStar);

    @Modifying(clearAutomatically = true)
    @Query("delete from Interaction i where i.user.id = :userId")
    void deleteByUserId(int userId);
}
