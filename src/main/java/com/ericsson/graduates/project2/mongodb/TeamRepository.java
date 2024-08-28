package com.ericsson.graduates.project2.mongodb;

import com.ericsson.graduates.project2.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends MongoRepository<Team,Integer> {
    @Query("{ 'id' : ?0 }")
    Team teamFindByFirstId(int id);
}
