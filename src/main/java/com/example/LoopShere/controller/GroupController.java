package com.example.LoopShere.controller;

import com.example.LoopShere.model.Group;
import com.example.LoopShere.model.Message;
import com.example.LoopShere.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@CrossOrigin(origins = "*")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody GroupRequest request) {
        try {
            Group group = groupService.createGroup(request.getName(), request.getMemberEmails());
            return ResponseEntity.ok(group);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping("/{groupId}/send")
    public ResponseEntity<?> sendGroupMessage(@PathVariable Long groupId, @RequestBody MessageRequest request) {
        try {
            Message message = groupService.sendGroupMessage(groupId, request.getSenderEmail(), request.getContent());
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    @GetMapping("/{groupId}/chat")
    public ResponseEntity<List<Message>> getGroupChat(@PathVariable Long groupId) {
        List<Message> messages = groupService.getGroupChat(groupId);
        return ResponseEntity.ok(messages);
    }
}

class GroupRequest {
    private String name;
    private List<String> memberEmails;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getMemberEmails() { return memberEmails; }
    public void setMemberEmails(List<String> memberEmails) { this.memberEmails = memberEmails; }
}

class MessageRequest {
    private String senderEmail;
    private String content;

    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}