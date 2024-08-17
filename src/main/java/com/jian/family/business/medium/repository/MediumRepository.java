package com.jian.family.business.medium.repository;

import com.jian.family.business.medium.entity.MediumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MediumRepository extends JpaRepository<MediumEntity, Long> {


    @Query("select me from MediumEntity me where me.name like %?1%")
    Page<MediumEntity> findByNameLike(String name, Pageable pageable);
}