package com.green.acamatch.accessLog.dailyVisitorStatus;

import com.green.acamatch.entity.dailyVisitorStatus.MonthlyVisitorStat;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("visitor")
@RequiredArgsConstructor
public class DailyVisitorStatController {

    private final DailyVisitorStatService dailyVisitorStatService;
    private final MonthlyVisitorStatRepository monthlyVisitorStatRepository;


    @PostMapping("/track")
    public ResponseEntity<Map<String, Object>> trackVisitor(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        String ipAddress = request.getRemoteAddr();

        System.out.println("API 요청: visitor/track - Session ID: " + sessionId + ", IP: " + ipAddress);

        dailyVisitorStatService.saveOrUpdateVisitor(sessionId, ipAddress);

        // JSON 응답을 반환하도록 수정
        Map<String, Object> response = new HashMap<>();
        response.put("resultMessage", "Visitor tracked successfully");
        response.put("status", "success");

        return ResponseEntity.ok(response);
    }


    @GetMapping("/recent-visitors")
    public ResponseEntity<Long> getRecentVisitors() {
        long count = dailyVisitorStatService.countRecentVisitors();
        return ResponseEntity.ok(count);
    }

    // 특정 월의 방문자 통계 조회
    @GetMapping("/monthly-stats")
    public ResponseEntity<Map<String, Object>> getMonthlyStats(@RequestParam String yearMonth) {
        System.out.println("월간 방문자 통계 조회 요청: " + yearMonth);

        YearMonth targetMonth = YearMonth.parse(yearMonth);
        Optional<MonthlyVisitorStat> monthlyStat = monthlyVisitorStatRepository.findByMonth(targetMonth);

        if (monthlyStat.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("yearMonth", yearMonth);
            response.put("totalVisits", monthlyStat.get().getTotalVisits());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
