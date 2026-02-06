package com.inetz.receipt.feescontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inetz.receipt.model.DashboardResponse;
import com.inetz.receipt.response.ApiResponse;
import com.inetz.receipt.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/overall")
    public ApiResponse<DashboardResponse> getOverallDashboard() {
        return new ApiResponse<>(
                "Overall dashboard fetched successfully",
                dashboardService.getOverallDashboard(),
                true
        );
    }
}
