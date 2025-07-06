package com.alejandroct.minot_api.note.filter;

import com.alejandroct.minot_api.note.model.Note;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class NoteSpecification {
    public static Specification<Note> withFilters(String text, Boolean trashed, String email) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("user").get("email"), email));

            if (trashed != null) {
                predicates.add(cb.equal(root.get("trashed"), trashed));
            }

            if (StringUtils.hasText(text)) {
                String pattern = "%" + text.toLowerCase() + "%";

                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), pattern),
                        cb.like(cb.lower(root.get("content")), pattern))
                );
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
