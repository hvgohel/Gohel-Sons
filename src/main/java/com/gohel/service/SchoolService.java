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
      String invoiceHtml =
          "<table style=\"border: 2px solid; width: 100%; padding: 0 20px; color: SteelBlue; font-size: 20px\">\n" + "    <tr>\n" + "        <td colspan=\"3\" style=\"padding: 20px 0;\">\n" + "            <h1 style=\"color: green; text-align: center;\">INVOICE</h1>\n" + "        </td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td style=\"width: 40%;\"><b>Date:</b></td>\n" + "        <td><b>To:<b></td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td>{deliveryDate}</td>\n" + "        <td>{name}</td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td></td>\n" + "        <td colspan=\"2\">{address}</td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td><b>Invoice:<b></td>\n" + "        <td><b>Contact Info:<b></td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td>{invoice}</td>\n" + "        <td>{contact}</td>\n" + "    </tr>\n" + "    <tr>\n" + "        <th colspan=\"2\" style=\"padding-top: 100px\"></th>\n" + "    </tr>\n" + "    <tr>\n" + "        <th style=\"text-align: left; border: 1px solid\">Description</th>\n" + "        <th width=\"15%\" style=\"text-align: left; border: 1px solid\">Quantity</th>\n" + "        <th width=\"15%\" style=\"text-align: left; border: 1px solid\">Amount</th>\n" + "    </tr>\n" + "    <tr>\n" + "        <td>{description}</td>\n" + "        <td>{quantity}</td>\n" + "        <td>{amount}</td>\n" + "    </tr>\n" + "    <td colspan=\"3\" style=\"padding-top: 200px\">\n" + "        <h2 style=\"color: green; text-align: center;\">Gohel &amp; Son's | Location : Dhoraji, Gujarat | Mobile: 1234567890</h2>\n" + "    </td>\n" + "    </tr>\n" + "</table>";
      invoiceHtml =
          invoiceHtml.replace("{name}", school.getName()).replace("{address}", school.getAddress())
              .replace("{contact}", school.getEmail())
              .replace("{deliveryDate}", school.getDeliveryDate())
              .replace("{invoice}", school.getInvoice()).replace("{description}", description)
              .replace("{quantity}", totalQuantity.toString())
              .replace("{amount}", amount.toString());
      HtmlConverter.convertToPdf(invoiceHtml, response.getOutputStream());
    }
  }
}
