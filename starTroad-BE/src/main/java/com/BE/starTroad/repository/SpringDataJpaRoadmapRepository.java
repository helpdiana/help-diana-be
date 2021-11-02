package com.BE.starTroad.repository;

import com.BE.starTroad.domain.Roadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SpringDataJpaRoadmapRepository extends JpaRepository<Roadmap, Long> {

    public Roadmap findById(int id);
    public List<Roadmap> findByNameLike(String name);
    public List<Roadmap> findByGenerator(String generator);
    public List<Roadmap> findByTagLike(String tag);
    public List<Roadmap> findByOwner(String owner);
}
