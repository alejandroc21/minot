package com.alejandroct.minot_api.folder.repository;

import com.alejandroct.minot_api.folder.model.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {


    @Query("""
            SELECT f FROM  Folder f WHERE f.user.email = :userEmail
                AND (:parentId IS NULL AND  f.parent IS NULL
                OR :parentId IS NOT NULL AND f.parent.id = :parentId)
            """)
    Page<Folder> findUserByEmailAndParentId(@Param("userEmail") String userEmail,
                                            @Param("parentId") Long parentId, Pageable pageable);

    Page<Folder> findByUserEmailAndParentIsNull(String userEmail, Pageable pageable);

    Page<Folder> findByUserEmailAndParentId(String userEmail, Long parentId, Pageable pageable);

    Optional<Folder> findByIdAndUserEmail(Long id, String email);
}
