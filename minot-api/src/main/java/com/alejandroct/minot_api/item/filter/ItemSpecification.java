package com.alejandroct.minot_api.item.filter;

import com.alejandroct.minot_api.item.dto.ItemFilter;
import com.alejandroct.minot_api.item.dto.ItemType;
import com.alejandroct.minot_api.item.model.Item;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemSpecification {
    public static Specification<Item> withFilters(ItemFilter filter, String email){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("user").get("email"), email));

            if(filter.type() !=null && !filter.type().isEmpty()){
                List<String> types = filter.type().stream().map(Enum::name).toList();
                predicates.add(root.get("type").in(types));
            }

            if(filter.trashed() != null){
                predicates.add(cb.equal(root.get("trashed"), filter.trashed()));
            }

            if(StringUtils.hasText(filter.text())){
                String pattern = "%"+filter.text().toLowerCase()+"%";

                Predicate folder = cb.and(
                        cb.equal(root.type(), ItemType.FOLDER.name()),
                        cb.like(cb.lower(root.get("name")), pattern));

                Predicate content = cb.and(
                        root.type().in(ItemType.NOTE.name()),
                        cb.or(
                                cb.like(cb.lower(root.get("name")),pattern),
                                cb.like(cb.lower(root.get("content")), pattern)
                        )
                );

                predicates.add(cb.or(folder, content));
            }


            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

//    public static Specification<Item> byUserEmail(String email) {
//        return (root, query, cb) ->
//                cb.equal(root.get("user").get("email"), email);
//    }

}
