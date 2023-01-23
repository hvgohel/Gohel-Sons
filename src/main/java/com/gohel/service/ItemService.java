package com.gohel.service;

import com.gohel.model.Item;
import com.gohel.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import static com.gohel.utils.Constants.DESC;
import static com.gohel.utils.Constants.DISPLAY_RECORD;

@Service
@RequiredArgsConstructor
public class ItemService extends AbstractService<Item, Long> {

  private final ItemRepository itemRepository;
  private final UserService userService;

  @Override
  protected JpaRepository<Item, Long> getRepository() {
    return itemRepository;
  }

  public Page<Item> getItems(Integer pageNumber) {
    return itemRepository.findAllByUser(userService.currentUser(),
        PageRequest.of(pageNumber - 1, DISPLAY_RECORD, DESC, "id"));
  }

  public Page<Item> search(Integer pageNumber, String search) {
    String searchLike = "%" + search + "%";
    return itemRepository.search(userService.currentUser().getId(), searchLike, searchLike,
        searchLike, search, search, search,
        PageRequest.of(pageNumber - 1, DISPLAY_RECORD, DESC, "id"));
  }

  public void upload(Long id, byte[] content) {
    Item item = itemRepository.findById(id).get();
    item.setReceipt(Base64Utils.encodeToString(content));
    itemRepository.save(item);
  }
}
