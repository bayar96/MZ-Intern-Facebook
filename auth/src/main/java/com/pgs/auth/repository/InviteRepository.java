package com.pgs.auth.repository;

import com.pgs.auth.domain.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

    Optional<Invite> findByEventIdAndInvitedUser_Id(Long eventId, Long userId);

    List<Invite> findAllByEventId(Long eventId);
}
