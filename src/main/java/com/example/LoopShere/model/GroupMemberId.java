package com.example.LoopShere.model;

import java.io.Serializable;
import java.util.Objects;

public class GroupMemberId implements Serializable {

    private Long group; // Matches the 'group' field in GroupMember
    private String userEmail; // Matches the 'userEmail' field in GroupMember

    // Constructors
    public GroupMemberId() {}
    public GroupMemberId(Long group, String userEmail) {
        this.group = group;
        this.userEmail = userEmail;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMemberId that = (GroupMemberId) o;
        return Objects.equals(group, that.group) && Objects.equals(userEmail, that.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, userEmail);
    }
}