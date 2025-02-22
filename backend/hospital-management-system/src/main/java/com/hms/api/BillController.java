package com.hms.api;


import com.hms.dto.BillRequest;
import com.hms.dto.BillResponse;
import com.hms.dto.PdfBillDTO;
import com.hms.service.BillService;
import com.hms.service.PdfGeneratorService;
import com.hms.util.HtmlTemplateProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;
    private final PdfGeneratorService pdfGeneratorService;
    private final HtmlTemplateProcessor htmlTemplateProcessor;

    // ✅ Unified API for Create/Update
    @PostMapping
    public ResponseEntity<BillResponse> createBill(@RequestBody BillRequest request) {
        log.info("Received request to create bill");
        return ResponseEntity.ok(billService.saveBill(null, request)); // billId = null for create
    }

    @PutMapping("/{billId}")
    public ResponseEntity<BillResponse> updateBill(@PathVariable Long billId, @RequestBody BillRequest request) {
        log.info("Received request to update bill with ID: {}", billId);
        return ResponseEntity.ok(billService.saveBill(billId, request));
    }

    // ✅ Get All Bills
    @GetMapping
    public ResponseEntity<List<BillResponse>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    // ✅ Get Bill by ID
    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getBillById(@PathVariable Long id) {
        return ResponseEntity.ok(billService.getBillById(id));
    }

    // ✅ Search Bills by Multiple Params
    @GetMapping("/search")
    public ResponseEntity<List<BillResponse>> searchBills(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean paymentDone) {
        return ResponseEntity.ok(billService.searchBills(keyword, paymentDone));
    }


    // ✅ Delete Bill
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.ok("Bill deleted successfully.");
    }

    @GetMapping("/{billId}/generate-pdf")
    public ResponseEntity<FileSystemResource> generatePdf(@PathVariable Long billId) throws Exception {
        String templatePath = "src/main/resources/templates/bills/OPD-Bill-1.html";
        String outputPath = "src/main/resources/generated/OPD-Bill-" + billId + ".pdf";

        PdfBillDTO billDTO = billService.getBillDetails(billId);
        File pdfFile = pdfGeneratorService.generatePdfFromTemplate(templatePath, outputPath, billDTO);

        FileSystemResource resource = new FileSystemResource(pdfFile);
        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfFile.getName())
                             .contentType(MediaType.APPLICATION_PDF)
                             .body(resource);
    }


}
