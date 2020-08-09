package business.custom.impl;

import business.custom.ItemBO;
import dao.DAOFactory;
import dao.DAOType;
import dao.custom.ItemDAO;
import entity.Item;
import util.ItemTM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    public  String getNewItemId() throws Exception {
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.Item);
        String lastItemId = itemDAO.getLastItemId();
        if (lastItemId == null) {
            return "I001";
        } else {
            int maxCode;
            maxCode = Integer.parseInt(itemDAO.getLastItemId().replace("I", ""));
            maxCode = maxCode + 1;
            String code = "";
            if (maxCode < 10) {
                code = "I00" + maxCode;
            } else if (maxCode < 100) {
                code = "I0" + maxCode;
            } else {
                code = "I" + maxCode;
            }
            return code;
        }
    }


    public List<ItemTM> getAllItems() throws Exception {
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.Item);
        List<Item> items = itemDAO.findAll();
        List<ItemTM> allItems = new ArrayList();
        for (Item item : items) {
            allItems.add(new ItemTM(item.getCode(), item.getDescription(),
                    item.getQtyOnHand(), item.getUnitPrice().doubleValue()));
        }
        return allItems;
    }

    public  boolean saveItem(String code, String description, int qtyOnHand, double unitPrice) throws Exception {
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.Item);
        return itemDAO.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }


    public  boolean deleteItem(String itemCode) throws Exception {
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.Item);
        return itemDAO.delete(itemCode);
    }


    public  boolean updateItem(String code, String description, int qtyOnHand, double unitPrice) throws Exception {
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.Item);
        return itemDAO.update(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }


}
