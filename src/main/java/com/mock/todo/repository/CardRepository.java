package com.mock.todo.repository;

import com.mock.todo.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {

    List<CardEntity> findAllByRemoveStatusIs(boolean isRemove);

}
