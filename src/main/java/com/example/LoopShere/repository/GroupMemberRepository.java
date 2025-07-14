package com.example.LoopShere.repository;

import com.example.LoopShere.model.GroupMember;
import com.example.LoopShere.model.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
}