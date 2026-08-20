package com.nehant.daily_habit_tracker.controller;

import com.nehant.daily_habit_tracker.dto.XPResponse;
import com.nehant.daily_habit_tracker.service.XPService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nehant.daily_habit_tracker.dto.LevelResponse;

@RestController
@RequestMapping("/xp")
public class XPController {

    private final XPService xpService;

    public XPController(XPService xpService) {
        this.xpService = xpService;
    }

    @GetMapping("/{habitId}")
    public ResponseEntity<XPResponse> getTotalXP(
            @PathVariable Long habitId) {

        int totalXP = xpService.getTotalXP(habitId);

        XPResponse response = new XPResponse(
                habitId,
                totalXP
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{habitId}/level")
    public ResponseEntity<LevelResponse> getLevel(
            @PathVariable Long habitId) {

        int totalXP = xpService.getTotalXP(habitId);
        int level = xpService.getLevel(habitId);

        LevelResponse response = new LevelResponse(
                habitId,
                totalXP,
                level
        );

        return ResponseEntity.ok(response);
    }
}