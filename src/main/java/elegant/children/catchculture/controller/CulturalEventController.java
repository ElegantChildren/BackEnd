package elegant.children.catchculture.controller;

import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventDetailsResponseDTO;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.service.culturalEvent.CulturalEventService;
import elegant.children.catchculture.service.interaction.InteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cultural-event")
public class CulturalEventController {

    private final CulturalEventService culturalEventService;
    private final InteractionService interactionService;

    @GetMapping("/{culturalEventId}")
    public ResponseEntity<CulturalEventDetailsResponseDTO> getCulturalEventDetails(final @PathVariable int culturalEventId, final @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(culturalEventService.getCulturalEventDetails(culturalEventId, user));
    }

    @PostMapping("/{culturalEventId}/like")
    public ResponseEntity<Void> likeCulturalEvent(final @PathVariable int culturalEventId, final @AuthenticationPrincipal User user) {

        interactionService.likeCulturalEvent(culturalEventId, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{culturalEventId}/star")
    public ResponseEntity<Void> starCulturalEvent(final @PathVariable int culturalEventId, final @AuthenticationPrincipal User user) {
        interactionService.starCulturalEvent(culturalEventId, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{culturalEventId}/like")
    public ResponseEntity<Void> deleteLikeCulturalEvent(final @PathVariable int culturalEventId, final @AuthenticationPrincipal User user) {
        interactionService.deleteLikeCulturalEvent(culturalEventId, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{culturalEventId}/star")
    public ResponseEntity<Void> deleteStarCulturalEvent(final @PathVariable int culturalEventId, final @AuthenticationPrincipal User user) {
        interactionService.deleteStarCulturalEvent(culturalEventId, user);
        return ResponseEntity.ok().build();
    }

}