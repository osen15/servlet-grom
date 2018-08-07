package service;

import dao.ItemDao;
import entity.Item;

public class ItemService {
    private static ItemDao itemDao = new ItemDao();

    public static Item findItem(Long id) {
        return itemDao.findById(id);
    }

    public static void saveItem(Item item) {
        itemDao.save(item);
    }

    public static void deleteItem(Long id) throws Exception {
        itemDao.delete(id);
    }

    public static void updateItem(Item item) throws Exception {
        itemDao.update(item);
    }


}
