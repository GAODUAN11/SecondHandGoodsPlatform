package org.example.secondhandgoodsplatform.service;

import org.example.secondhandgoodsplatform.dao.ItemDAO;
import org.example.secondhandgoodsplatform.model.Item;
import java.util.List;

public class ItemService {
    private ItemDAO itemDAO = new ItemDAO();

    public boolean addItem(String name, String description, double price, int userId) {
        Item item = new Item(name, description, price, userId);
        return itemDAO.createItem(item);
    }

    public List<Item> getAllItems() {
        return itemDAO.findAll();
    }

    public List<Item> searchItemsByName(String namePattern) {
        return itemDAO.findByNameContaining(namePattern);
    }

    public Item getItemById(int id) {
        return itemDAO.findById(id);
    }

    public boolean updateItem(int id, String name, String description, double price) {
        Item item = itemDAO.findById(id);
        if (item != null) {
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            return itemDAO.updateItem(item);
        }
        return false;
    }

    public boolean deleteItem(int id) {
        return itemDAO.deleteItem(id);
    }

    public List<Item> getItemsByUserId(int userId) {
        return itemDAO.findByUserId(userId);
    }
}