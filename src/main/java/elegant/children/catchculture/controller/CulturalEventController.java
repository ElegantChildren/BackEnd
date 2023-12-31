package elegant.children.catchculture.controller;

import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventDetailsResponseDTO;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventListResponseDTO;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventMapResponseDTO;
import elegant.children.catchculture.entity.culturalevent.Category;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.common.constant.SortType;
import elegant.children.catchculture.service.culturalEvent.CulturalEventService;
import elegant.children.catchculture.service.interaction.InteractionService;
import elegant.children.catchculture.service.visitAuth.VisitAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cultural-event")
public class CulturalEventController {

    private final CulturalEventService culturalEventService;
    private final InteractionService interactionService;
    private final VisitAuthService visitAuthService;

    @GetMapping
    public ResponseEntity<List<CulturalEventListResponseDTO>> getCulturalEventMainList() {
        return ResponseEntity.ok(culturalEventService.getCulturalEventMainList());
    }


    @GetMapping("/list")
    public ResponseEntity<Page<CulturalEventListResponseDTO>> getCulturalEventList(final @RequestParam(required = false) List<Category> category,
                                                                                   final @RequestParam(required = false, defaultValue = "0") int offset,
                                                                                   final @RequestParam(required = false, defaultValue = "RECENT") SortType sortType) {

        return ResponseEntity.ok(culturalEventService.getCulturalEventList(category, offset, sortType));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CulturalEventListResponseDTO>> searchCulturalEventList(final @RequestParam String keyword,
                                                                                      final @RequestParam(required = false, name = "category") List<Category> categoryList,
                                                                                      final @RequestParam(required = false, defaultValue = "0") int offset,
                                                                                      final @RequestParam(required = false, defaultValue = "RECENT") SortType sortType) {
        return ResponseEntity.ok(culturalEventService.searchCulturalEventListWithCondition(keyword, categoryList, offset, sortType));
    }

    @GetMapping("/{culturalEventId}")
    public ResponseEntity<CulturalEventDetailsResponseDTO> getCulturalEventDetails(final @PathVariable int culturalEventId,
                                                                                   final @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(culturalEventService.getCulturalEventDetails(culturalEventId, user));
    }


    @GetMapping("/{culturalEventId}/title")
    public ResponseEntity<String> getCulturalEventtitle(final @PathVariable int culturalEventId,
                                                                                   final @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(culturalEventService.getCulturalEventTitle(culturalEventId, user));
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

    @GetMapping("/map")
    public ResponseEntity<List<CulturalEventMapResponseDTO>> getCulturalEventMapList() {

        return ResponseEntity.ok(culturalEventService.getCulturalEventMapList());
    }

    @PostMapping(value = "/visit-auth", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createVisitAuth(
            final @AuthenticationPrincipal User user,
            final @RequestPart int eventId,
            final @RequestPart("fileList") List<MultipartFile> fileList) {
        visitAuthService.createVisitAuth(user,eventId,fileList);
    }

}
