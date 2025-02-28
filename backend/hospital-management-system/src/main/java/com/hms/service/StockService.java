package com.hms.service;


import com.hms.dto.StockRequest;
import com.hms.dto.StockResponse;
import com.hms.exception.MedicineNotFoundException;
import com.hms.exception.StockNotFoundException;
import com.hms.modal.Medicine;
import com.hms.repository.MedicineRepository;
import com.hms.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StockRepository stockRepository;
    private final MedicineRepository medicineRepository;

    // ✅ Add stock
    @Transactional
    public StockResponse addStock(StockRequest request) {
        log.info("Adding stock for medicine ID: {}", request.getMedicineId());

        Medicine medicine = medicineRepository.findById(request.getMedicineId())
                                              .orElseThrow(() -> new MedicineNotFoundException("Medicine not found with ID: " + request.getMedicineId()));

        Stock stock = stockRepository.findByMedicineId(request.getMedicineId());
        if (stock == null) {
            stock = new Stock(null, medicine, request.getQuantity(), medicine.getReorderLevel());
        } else {
            stock.setAvailableQuantity(stock.getAvailableQuantity() + request.getQuantity());
        }

        Stock savedStock = stockRepository.save(stock);
        return mapToResponse(savedStock);
    }

    // ✅ Reduce stock
    @Transactional
    public StockResponse reduceStock(StockRequest request) {
        log.info("Reducing stock for medicine ID: {}", request.getMedicineId());

        Stock stock = stockRepository.findByMedicineId(request.getMedicineId());
        if (stock == null) {
            throw new StockNotFoundException("Stock not found for medicine ID: " + request.getMedicineId());
        }

        if (stock.getAvailableQuantity() < request.getQuantity()) {
            throw new IllegalStateException("Insufficient stock for medicine ID: " + request.getMedicineId());
        }

        stock.setAvailableQuantity(stock.getAvailableQuantity() - request.getQuantity());
        Stock savedStock = stockRepository.save(stock);
        return mapToResponse(savedStock);
    }

    // ✅ Get stock details
    public StockResponse getStock(Long medicineId) {
        Stock stock = stockRepository.findByMedicineId(medicineId);
        if (stock == null) {
            throw new StockNotFoundException("Stock not found for medicine ID: " + medicineId);
        }
        return mapToResponse(stock);
    }

    // ✅ Get low stock medicines
    public List<StockResponse> getLowStockMedicines() {
        List<Stock> lowStock = stockRepository.findByAvailableQuantityLessThanEqual(10); // Fetch medicines with stock ≤ 10
        return lowStock.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // ✅ Adjust stock manually
    @Transactional
    public StockResponse adjustStock(StockRequest request) {
        log.info("Adjusting stock for medicine ID: {}", request.getMedicineId());

        Stock stock = stockRepository.findByMedicineId(request.getMedicineId());
        if (stock == null) {
            throw new StockNotFoundException("Stock not found for medicine ID: " + request.getMedicineId());
        }

        stock.setAvailableQuantity(request.getQuantity());
        Stock savedStock = stockRepository.save(stock);
        return mapToResponse(savedStock);
    }

    // ✅ Helper method to map entity to response
    private StockResponse mapToResponse(Stock stock) {
        return new StockResponse(stock.getMedicine().getId(), stock.getMedicine().getName(), stock.getAvailableQuantity(), stock.getReorderLevel());
    }
}
