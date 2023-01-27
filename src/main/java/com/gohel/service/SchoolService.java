package com.gohel.service;

import com.gohel.model.School;
import com.gohel.model.Student;
import com.gohel.repository.SchoolRepository;
import com.gohel.repository.StudentRepository;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.gohel.utils.Constants.DESC;
import static com.gohel.utils.Constants.DISPLAY_RECORD;
import static com.gohel.utils.Utils.getInvoiceHtml;

@Service
@RequiredArgsConstructor
public class SchoolService extends AbstractService<School, Long> {

  private final SchoolRepository schoolRepository;
  private final StudentRepository studentRepository;
  private final UserService userService;

  @Override
  protected JpaRepository<School, Long> getRepository() {
    return schoolRepository;
  }

  public Page<School> getSchools(Integer pageNumber) {
    return schoolRepository.findAllByUser(userService.currentUser(),
        PageRequest.of(pageNumber - 1, DISPLAY_RECORD, DESC, "id"));
  }

  public Page<School> getSchoolsBySearch(Integer pageNumber, String search) {
    return schoolRepository.findAllByUserAndNameLike(userService.currentUser(), "%" + search + "%",
        PageRequest.of(pageNumber - 1, DISPLAY_RECORD, DESC, "id"));
  }

  public List<School> getAllSchool() {
    return schoolRepository.findAllByUser(userService.currentUser());
  }

  public void invoice(HttpServletResponse response, Long id) throws IOException {
    School school = schoolRepository.findById(id).get();
    if (school != null && StringUtils.isEmpty(school.getInvoice())) {
      school.setInvoice("INV_SCHOOL_" + school.getId());
      schoolRepository.save(school);
    }

    List<Student> students = studentRepository.findAllBySchoolId(school.getId());
    if (!CollectionUtils.isEmpty(students)) {
      Integer totalShirt = 0;
      Integer totalPent = 0;
      Integer totalQuantity = 0;
      Integer amount = 0;
      for (Student student : students) {
        totalShirt += student.getNoofShirtKurti() != null ? student.getNoofShirtKurti() : 0;
        totalPent += student.getNoofPentSalwar() != null ? student.getNoofPentSalwar() : 0;
        amount += student.getPrice();
      }

      totalQuantity = totalShirt + totalPent;
      String description = "Shirt: " + totalShirt + ", Pent: " + totalPent;
      String invoiceHtml = getInvoiceHtml(school.getName(), school.getAddress(), school.getEmail(),
          school.getDeliveryDate(), school.getInvoice(), description, totalQuantity.toString(),
          amount.toString(), school.getUser().getCompanyName(),
          school.getUser().getCompanyAddress(), school.getUser().getMobile());
      HtmlConverter.convertToPdf(invoiceHtml, response.getOutputStream());
    }
  }
}
