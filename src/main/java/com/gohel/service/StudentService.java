package com.gohel.service;

import com.gohel.model.School;
import com.gohel.model.Student;
import com.gohel.repository.StudentRepository;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.gohel.utils.Constants.*;
import static com.gohel.utils.Utils.getInvoiceHtml;

@Service
@RequiredArgsConstructor
public class StudentService extends AbstractService<Student, Long> {

  private final StudentRepository studentRepository;
  private final UserService userService;

  @Override
  protected JpaRepository<Student, Long> getRepository() {
    return studentRepository;
  }

  public Page<Student> getStudents(Integer pageNumber) {
    return studentRepository.findAllByUserAndSchoolIdNotNull(userService.currentUser(),
        PageRequest.of(pageNumber - 1, DISPLAY_RECORD, DESC, "id"));
  }

  public Page<Student> searchStudent(Integer pageNumber, String search) {
    String searchLike = "%" + search + "%";
    return studentRepository.searchStudent(userService.currentUser().getId(), searchLike, search,
        search, searchLike, search, PageRequest.of(pageNumber - 1, DISPLAY_RECORD, ASC, "id"));
  }

  public Page<Student> getCustomers(Integer pageNumber) {
    return studentRepository.findAllByUserAndSchoolIdNull(userService.currentUser(),
        PageRequest.of(pageNumber - 1, DISPLAY_RECORD, ASC, "deliveryDate"));
  }

  public Page<Student> searchCustomer(Integer pageNumber, String search) {
    String searchLike = "%" + search + "%";
    return studentRepository.searchCustomer(userService.currentUser().getId(), searchLike,
        searchLike, search, searchLike, search,
        PageRequest.of(pageNumber - 1, DISPLAY_RECORD, ASC, "delivery_date"));
  }

  public Page<Student> getAllByUser(Integer pageNumber) {
    return studentRepository.findAllByUser(userService.currentUser(),
        PageRequest.of(pageNumber - 1, DISPLAY_RECORD, ASC, "deliveryDate"));
  }

  public Page<Student> search(Integer pageNumber, String search) {
    String searchLike = "%" + search + "%";
    return studentRepository.search(userService.currentUser().getId(), searchLike, search, search,
        searchLike, PageRequest.of(pageNumber - 1, DISPLAY_RECORD, ASC, "deliveryDate"));
  }

  public Integer getTotalAmount(Long schoolId) {
    return studentRepository.sumBySchoolId(schoolId);
  }

  public Integer getTotalStudent(Long schoolId) {
    return studentRepository.countBySchoolId(schoolId);
  }

  public void invoice(HttpServletResponse response, Long id) throws IOException {
    Student student = studentRepository.findById(id).get();
    if (student != null && StringUtils.isEmpty(student.getInvoice())) {
      student.setInvoice("INV_CUSTOMER_" + student.getId());
      studentRepository.save(student);
    }

    Integer noofShirtKurti = student.getNoofShirtKurti() != null ? student.getNoofShirtKurti() : 0;
    Integer noofPentSalwar = student.getNoofPentSalwar() != null ? student.getNoofPentSalwar() : 0;
    Integer totalQuantity = noofShirtKurti + noofPentSalwar;
    String description = student.getGender().equalsIgnoreCase("male") ?
        "Shirt: " + noofShirtKurti + ", Pent: " + noofPentSalwar :
        "Kurti: " + noofShirtKurti + ", Salwar: " + noofPentSalwar;
    String invoiceHtml =
        getInvoiceHtml(student.getName(), student.getAddress(), student.getMobile(),
            student.getDeliveryDate(), student.getInvoice(), description, totalQuantity.toString(),
            student.getPrice().toString(), student.getUser().getCompanyName(),
            student.getUser().getCompanyAddress(), student.getUser().getMobile());

    HtmlConverter.convertToPdf(invoiceHtml, response.getOutputStream());
  }
}
