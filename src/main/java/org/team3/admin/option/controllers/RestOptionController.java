package org.team3.admin.option.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.team3.admin.option.service.OptionConfigInfoService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/option")
@RequiredArgsConstructor
public class RestOptionController {

    private final OptionConfigInfoService optionConfigInfoService;

    @GetMapping("/members")
    public ResponseEntity<Map<String, Integer>> getMemberCount(@RequestParam(name = "selectedOption") String selectedOption) {
        try {
            System.out.println(selectedOption+"selectedOption");
            // Replace this with your actual logic to get the member count for the selected option
            int memberCount = optionConfigInfoService.getOptionpreMemberCount(selectedOption);

            // Create a map to hold the member count
            Map<String, Integer> responseMap = new HashMap<>();
            responseMap.put("memberCount", memberCount);

            // Return the member count as JSON
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            // Handle exceptions appropriately based on your application's needs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}