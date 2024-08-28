package com.ericsson.graduates.project2.mongodb;

import com.ericsson.graduates.project2.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends MongoRepository<Member,Integer> {
    @Query("{ 'id' : ?0 }")
    Member memberfindByFirstId(int id);
}
