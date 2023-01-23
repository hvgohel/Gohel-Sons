package com.gohel.service;

import com.gohel.model.Transaction;
import com.gohel.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static com.gohel.utils.Constants.DESC;
import static com.gohel.utils.Constants.DISPLAY_RECORD;

@Service
@RequiredArgsConstructor
public class TransactionService extends AbstractService<Transaction, Long> {

  private final TransactionRepository transactionRepository;
  private final UserService userService;

  @Override
  protected JpaRepository<Transaction, Long> getRepository() {
    return transactionRepository;
  }

  public Page<Transaction> getTransactions(Integer pageNumber) {
    return transactionRepository.getAllByUser(userService.currentUser().getId(),
        PageRequest.of(pageNumber - 1, DISPLAY_RECORD, DESC, "id"));
  }

  public Page<Transaction> search(Integer pageNumber, String search) {
    String searchLike = "%" + search + "%";
    return transactionRepository.search(userService.currentUser().getId(), searchLike, search,
        search, search, PageRequest.of(pageNumber - 1, DISPLAY_RECORD, DESC, "id"));
  }
}
