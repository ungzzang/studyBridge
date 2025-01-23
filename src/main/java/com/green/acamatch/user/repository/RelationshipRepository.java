package com.green.acamatch.user.repository;

import com.green.acamatch.user.entity.Relationship;
import com.green.acamatch.user.entity.RelationshipId;
import com.green.acamatch.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, RelationshipId> {
    Relationship findRelationshipByParentAndStudent(User parent, User student);

    List<Relationship> findRelationshipsByParentAndCertification(User parent, int certification);

    List<Relationship> findRelationshipsByStudentAndCertification(User student, int certification);

    Relationship findRelationshipByParentAndStudentAndCertification(User parent, User student, int certification);
}
