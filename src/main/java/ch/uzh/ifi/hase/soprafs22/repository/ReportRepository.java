package ch.uzh.ifi.hase.soprafs22.repository;

import ch.uzh.ifi.hase.soprafs22.entity.Report;
import ch.uzh.ifi.hase.soprafs22.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("reportRepository")
public interface ReportRepository extends JpaRepository<Report, Long> {

    Report findByReportId(Long reportId);

    List<Report> findBySession(Session session);

}