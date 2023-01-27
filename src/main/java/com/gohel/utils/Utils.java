package com.gohel.utils;

import com.gohel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import static com.gohel.utils.Constants.*;

public class Utils {
  public static void setPagination(Page<?> page, Model model, String search, Double total,
      User user) {
    int current = page.getNumber() + 1;
    int begin = Math.max(1, current - 5);
    int end = Math.min(begin + 10, page.getTotalPages());

    model.addAttribute(LIST, page);
    model.addAttribute(BEGIN_INDEX, begin);
    model.addAttribute(END_INDEX, end);
    model.addAttribute(CURRENT_INDEX, current);
    model.addAttribute(SEARCH, search);
    model.addAttribute(TOTAL, total);
    model.addAttribute(USER, user);
  }

  public static String getInvoiceHtml(String name, String address, String mobile,
      String deliveryDate, String invoiceNumber, String description, String totalQuantity,
      String price, String companyName, String companyAddress, String companyMobile) {
    String invoiceHtml =
        "<table style=\"border: 2px solid; width: 100%; padding: 0 20px; color: SteelBlue; font-size: 20px\">\n" + "    <tr>\n" + "        <td colspan=\"3\" style=\"padding: 20px 0;\">\n" + "            <h1 style=\"color: green; text-align: center;\">INVOICE</h1>\n" + "        </td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td style=\"width: 40%;\">\n" + "            <b>Date:</b>\n" + "        </td>\n" + "        <td>\n" + "            <b>To:</b>\n" + "        </td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td>{deliveryDate}</td>\n" + "        <td>{name}</td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td></td>\n" + "        <td colspan=\"2\">{address}</td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td>\n" + "            <b>Invoice:</b>\n" + "        </td>\n" + "        <td>\n" + "            <b>Contact Info:</b>\n" + "        </td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td>{invoice}</td>\n" + "        <td>{contact}</td>\n" + "    </tr>\n" + "    <tr>\n" + "        <th colspan=\"2\" style=\"padding-top: 100px\"></th>\n" + "    </tr>\n" + "    <tr>\n" + "        <th style=\"text-align: left; border: 1px solid\">Description</th>\n" + "        <th width=\"15%\" style=\"text-align: left; border: 1px solid\">Quantity</th>\n" + "        <th width=\"15%\" style=\"text-align: left; border: 1px solid\">Amount</th>\n" + "    </tr>\n" + "    <tr>\n" + "        <td>{description}</td>\n" + "        <td>{quantity}</td>\n" + "        <td>{amount}</td>\n" + "    </tr>\n" + "    <tr>\n" + "        <td colspan=\"3\" style=\"padding-top: 200px\">\n" + "            <h2 style=\"color: green; text-align: center;\">{companyName} | Location : {companyAddress} | Mobile: {companyMobile}</h2>\n" + "        </td>\n" + "    </tr>\n" + "</table>\n";
    invoiceHtml = invoiceHtml.replace("{name}", name).replace("{address}", address)
        .replace("{contact}", mobile).replace("{deliveryDate}", deliveryDate)
        .replace("{invoice}", invoiceNumber).replace("{description}", description)
        .replace("{quantity}", totalQuantity).replace("{amount}", price)
        .replace("{companyName}", companyName).replace("{companyAddress}", companyAddress)
        .replace("{companyMobile}", companyMobile);
    return invoiceHtml;
  }
}
