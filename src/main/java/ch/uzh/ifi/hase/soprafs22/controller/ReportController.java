package ch.uzh.ifi.hase.soprafs22.controller;

import ch.uzh.ifi.hase.soprafs22.entity.Report;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ReportGetDTO;
import ch.uzh.ifi.hase.soprafs22.rest.dto.ReportPostDTO;
import ch.uzh.ifi.hase.soprafs22.rest.mapper.ReportDTOMapper;
import ch.uzh.ifi.hase.soprafs22.service.ReportService;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Report Controller
 * This class is responsible for handling all REST request that are related to
 * the session.
 * The controller will receive the request and delegate the execution to the
 * ReportService and finally return the result.
 */

@RestController
public class ReportController {

    private final ReportService reportService;

    ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ReportGetDTO> getAllReports() {
        // fetch all sessions in the internal representation
        List<Report> sessionReports = reportService.getSessionReports();
        List<ReportGetDTO> reportGetDTOs = new ArrayList<>();

        // convert each session to the API representation
        for (Report report : sessionReports) {
            reportGetDTOs.add(ReportDTOMapper.INSTANCE.convertEntityToReportGetDTO(report));
        }
        return reportGetDTOs;

    }

    @GetMapping("/reports/{reportId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReportGetDTO getReportbyId(@PathVariable Long reportId) {

        Report report = reportService.getReport(reportId);

        return ReportDTOMapper.INSTANCE.convertEntityToReportGetDTO(report);
    }

    @PostMapping("/reports")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ReportGetDTO createReport(@RequestBody ReportPostDTO reportPostDTO) {
        // convert API session to internal representation
        Report reportInput = ReportDTOMapper.INSTANCE.convertReportPostDTOtoEntity(reportPostDTO);

        // create Report
        Report createdReport = reportService.createReport(reportInput);

        // convert internal representation of session back to API
        return ReportDTOMapper.INSTANCE.convertEntityToReportGetDTO(createdReport);
    }

}


