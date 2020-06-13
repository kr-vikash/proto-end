package com.protoend.repository;


import com.protoend.model.ProtoTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtoEndRepository extends CrudRepository<ProtoTest, Integer> {


}
