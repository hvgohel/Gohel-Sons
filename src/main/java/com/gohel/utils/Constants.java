package com.gohel.utils;

import org.springframework.data.domain.Sort;

import static com.gohel.utils.API.*;

public interface Constants {
  String ENABLE_MODULE = "/stocks/index";
  String LIST = "list";
  String TOTAL = "total";
  String BEGIN_INDEX = "beginIndex";
  String END_INDEX = "endIndex";
  String CURRENT_INDEX = "currentIndex";
  String USER = "user";
  String SEARCH = "search";
  String REDIRECT = "redirect:";
  Integer DISPLAY_RECORD = 100;
  Sort.Direction DESC = Sort.Direction.DESC;
  Sort.Direction ASC = Sort.Direction.ASC;

  // User constants
  String USER_REDIRECT_LOGIN = REDIRECT.concat("/login");
  String USER_REDIRECT_REGISTRATION = "registration";
  String USER_REDIRECT_PROFILE = "profile/form";

  // Dashboard constants
  String DASHBOARD_REDIRECT_INDEX = "dashboard/index";

  // Student constants
  String STUDENTS_REDIRECT = REDIRECT.concat(STUDENTS);
  String STUDENTS_REDIRECT_INDEX = REDIRECT.concat(STUDENTS).concat(INDEX);
  String STUDENTS_REDIRECT_LIST = "students/list";
  String STUDENTS_REDIRECT_FORM = "students/form";

  // Customer constants
  String CUSTOMERS_REDIRECT = REDIRECT.concat(CUSTOMERS);
  String CUSTOMERS_REDIRECT_INDEX = REDIRECT.concat(CUSTOMERS).concat(INDEX);
  String CUSTOMERS_REDIRECT_LIST = "customers/list";
  String CUSTOMERS_REDIRECT_FORM = "customers/form";

  // School constants
  String SCHOOLS_REDIRECT = REDIRECT.concat(SCHOOLS);
  String SCHOOLS_REDIRECT_INDEX = REDIRECT.concat(SCHOOLS).concat(INDEX);
  String SCHOOLS_REDIRECT_LIST = "schools/list";
  String SCHOOLS_REDIRECT_FORM = "schools/form";

  // Stocks constants
  String STOCKS_REDIRECT = REDIRECT.concat(STOCKS);
  String STOCKS_REDIRECT_INDEX =  REDIRECT.concat(STOCKS).concat(INDEX);
  String STOCKS_REDIRECT_LIST = "stocks/list";
  String STOCK_REDIRECT_FORM = "stocks/form";

  // Transactions constants
  String TRANSACTIONS_REDIRECT = REDIRECT.concat(TRANSACTIONS);
  String TRANSACTIONS_REDIRECT_INDEX =  REDIRECT.concat(TRANSACTIONS).concat(INDEX);
  String TRANSACTIONS_REDIRECT_LIST = "transactions/list";
  String TRANSACTIONS_REDIRECT_FORM = "transactions/form";
}
