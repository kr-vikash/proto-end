package com.protoend.repository;


import com.protoend.model.ProtoEnd;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtoEndRepository extends CrudRepository<ProtoEnd, Integer> {


}
