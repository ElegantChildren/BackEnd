package elegant.children.catchculture.service.interaction;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import elegant.children.catchculture.entity.interaction.Interaction;
import elegant.children.catchculture.entity.interaction.LikeStar;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventRepository;
import elegant.children.catchculture.repository.interaction.InteractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class InteractionService {

    private final InteractionRepository interactionRepository;
    private final CulturalEventRepository culturalEventRepository;


    public void likeCulturalEvent(final int culturalEventId, final User user) {
        final CulturalEvent culturalEvent = culturalEventRepository.findById(culturalEventId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EVENT_ID));

        interactionRepository.findByUserIdAndCulturalEventIdAndLikeStar(user.getId(), culturalEventId, LikeStar.LIKE)
                .ifPresent(interaction -> {
                    throw new CustomException(ErrorCode.ALREADY_LIKE);
                });
        interactionRepository.save(Interaction.createInteraction(user, culturalEvent, LikeStar.LIKE));
        culturalEvent.addLikeCount();

    }

    public void starCulturalEvent(final int culturalEventId, final User user) {
        final CulturalEvent culturalEvent = culturalEventRepository.findById(culturalEventId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EVENT_ID));

        interactionRepository.findByUserIdAndCulturalEventIdAndLikeStar(user.getId(), culturalEventId, LikeStar.STAR)
                .ifPresent(interaction -> {
                    throw new CustomException(ErrorCode.ALREADY_STAR);
                });

        interactionRepository.save(Interaction.createInteraction(user, culturalEvent, LikeStar.STAR));
    }

    public void deleteLikeCulturalEvent(int culturalEventId, User user) {
        final CulturalEvent culturalEvent = culturalEventRepository.findById(culturalEventId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EVENT_ID));


        interactionRepository.findByUserIdAndCulturalEventIdAndLikeStar(user.getId(), culturalEventId, LikeStar.LIKE)
                .ifPresentOrElse(interaction -> {
                    interactionRepository.delete(interaction);
                },
                        () -> {
                            throw new CustomException(ErrorCode.NOT_LIKE);
                });
        culturalEvent.minusLikeCount();
    }

    public void deleteStarCulturalEvent(int culturalEventId, User user) {
        final CulturalEvent culturalEvent = culturalEventRepository.findById(culturalEventId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EVENT_ID));

        interactionRepository.findByUserIdAndCulturalEventIdAndLikeStar(user.getId(), culturalEventId, LikeStar.STAR)
                .ifPresentOrElse(interaction -> {
                    interactionRepository.delete(interaction);
                }, () -> {
                    throw new CustomException(ErrorCode.NOT_STAR);
                });
    }
}
