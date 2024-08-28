package com.ericsson.graduates.project2;
import java.util.List;

public interface IMemberRepository {

    Member getMember(int id );
    List<Member> getMembers();

    void insertMember(Member member);
    void updateMember(Member member);
    void deleteMember(int id);

}
