package com.example.LoopShere.service;

import com.example.LoopShere.model.Group;
import com.example.LoopShere.model.GroupMember;
import com.example.LoopShere.model.Message;
import com.example.LoopShere.repository.GroupMemberRepository;
import com.example.LoopShere.repository.GroupRepository;
import com.example.LoopShere.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Group createGroup(String name, List<String> memberEmails) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Group name cannot be null or empty");
        }
        System.out.println("Creating group with name: " + name + ", members: " + memberEmails);
        Group group = new Group();
        group.setName(name);
        Group savedGroup = groupRepository.save(group);

        for (String email : memberEmails) {
            GroupMember member = new GroupMember();
            member.setGroup(savedGroup);
            member.setUserEmail(email);
            groupMemberRepository.save(member);
        }
        return savedGroup;
    }

    public Message sendGroupMessage(Long groupId, String senderEmail, String content) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        Message message = new Message();
        message.setSenderEmail(senderEmail);
        message.setContent(content);
        message.setGroup(group);
        return messageRepository.save(message);
    }

    public List<Message> getGroupChat(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        return messageRepository.findByGroupOrderByTimestampAsc(group);
    }
}