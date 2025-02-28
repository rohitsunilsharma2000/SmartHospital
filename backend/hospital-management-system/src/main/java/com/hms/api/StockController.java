package com.hms.api;

import com.hms.dto.StockRequest;
import com.hms.dto.StockResponse;
import com.hms.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    // ✅ Add stock
    @PostMapping("/add")
    public ResponseEntity<StockResponse> addStock(@RequestBody StockRequest request) {
        return ResponseEntity.ok(stockService.addStock(request));
    }

    // ✅ Reduce stock
    @PostMapping("/reduce")
    public ResponseEntity<StockResponse> reduceStock(@RequestBody StockRequest request) {
        return ResponseEntity.ok(stockService.reduceStock(request));
    }

    // ✅ Get stock details
    @GetMapping("/{medicineId}")
    public ResponseEntity<StockResponse> getStock(@PathVariable Long medicineId) {
        return ResponseEntity.ok(stockService.getStock(medicineId));
    }

    // ✅ Get low stock medicines
    @GetMapping("/low")
    public ResponseEntity<List<StockResponse>> getLowStockMedicines() {
        return ResponseEntity.ok(stockService.getLowStockMedicines());
    }

    // ✅ Adjust stock manually
    @PutMapping("/adjust")
    public ResponseEntity<StockResponse> adjustStock(@RequestBody StockRequest request) {
        return ResponseEntity.ok(stockService.adjustStock(request));
    }
}
