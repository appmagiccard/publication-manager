package com.magicauction.publicationmanager.entity.repository;

import com.magicauction.publicationmanager.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
