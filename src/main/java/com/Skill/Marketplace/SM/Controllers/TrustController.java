package com.Skill.Marketplace.SM.Controllers;
import com.Skill.Marketplace.SM.Services.TrustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trust")
public class TrustController {

    @Autowired
    private TrustService trustService;

    @GetMapping("/provider/{username}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProviderTrust(@PathVariable String username) {
        return ResponseEntity.ok(trustService.getProviderTrustPublic(username));
    }

    @GetMapping("/consumer/{username}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getConsumerTrust(@PathVariable String username) {
        return ResponseEntity.ok(trustService.getConsumerTrustPublic(username));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyTrust() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(trustService.getMyTrust(username));
    }
}
