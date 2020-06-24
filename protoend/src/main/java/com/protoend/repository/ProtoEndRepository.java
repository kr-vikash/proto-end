package com.protoend.repository;


import com.protoend.model.ProtoEnd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtoEndRepository extends JpaRepository<ProtoEnd, Integer> {

}