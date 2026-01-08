package com.javarush.discussion.reaction;

import com.javarush.discussion.exception.ReactionNotFoundException;
import com.javarush.discussion.reaction.model.Reaction;
import com.javarush.discussion.reaction.model.ReactionKey;
import com.javarush.discussion.reaction.model.ReactionRequestTo;
import com.javarush.discussion.reaction.model.ReactionResponseTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ReactionService {
    private final ReactionRepository reactionRepository;
    private final ReactionDto mapper;

    public List<ReactionResponseTo> getAll() {
        return reactionRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public ReactionResponseTo getById(Long reactionId, Locale locale) {
        return reactionRepository.findByCountryAndId(String.valueOf(locale), reactionId)
                .map(mapper::toDto)
                .orElseThrow(() -> new ReactionNotFoundException("Reaction not found"));
    }

    @Transactional
    public ReactionResponseTo create(ReactionRequestTo dto, Locale locale) {
        ReactionKey key = ReactionKey.builder()
                .country(String.valueOf(locale))
                .id(generateId())
                .topicId(dto.getTopicId())
                .build();
        Reaction reaction = mapper.fromDto(dto);
        reaction.setReactionKey(key);
        // TODO: Присвоить state = APPROVE
        return mapper.toDto(reactionRepository.save(reaction));
    }

    private Long generateId() {
        long timestamp = System.currentTimeMillis() & 0x3FFFFFFFFFFL; // 42 bits
        long random = ThreadLocalRandom.current().nextLong() & 0x3FFFFFL; // 22 bits
        return (timestamp << 22) | random;
    }

    @Transactional
    public ReactionResponseTo update(ReactionRequestTo dto, Locale locale) {
        Long reactionId = dto.getId();
        String country = String.valueOf(locale);

        ReactionKey key = ReactionKey.builder()
                .country(country)
                .id(reactionId)
                .topicId(dto.getTopicId())
                .build();

        Optional<Reaction> existingReaction = reactionRepository.findById(key);

        if (existingReaction.isEmpty()) {
            throw new ReactionNotFoundException("Reaction not found with reactionId = " + reactionId);
        }
        existingReaction.get().setContent(dto.getContent());

        Reaction updatedReaction = reactionRepository.save(existingReaction.get());
        return mapper.toDto(updatedReaction);
    }

    @Transactional
    public boolean deleteById(Long reactionId, Locale locale) {
        if (reactionRepository.existsByCountryAndId(String.valueOf(locale), reactionId)) {
            log.debug("Deleting reaction with id = {}", reactionId);
            reactionRepository.deleteByCountryAndId(String.valueOf(locale), reactionId);
            return true;
        }
        return false;
    }
}
