package controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.Item;

import service.ItemService;

import javax.persistence.NoResultException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;


@WebServlet(urlPatterns = "/item")
public class ItemController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long itemID = 0;
        try {
            itemID = Long.parseLong(req.getParameter("id"));
            resp.getWriter().print(ItemService.findItem(itemID));
        } catch (NumberFormatException e) {
            resp.getWriter().print("/item?id=should be a number");
            e.printStackTrace();
        } catch (NoResultException e) {
            resp.getWriter().print("Item with id: " + itemID + " not found");

            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //req.getInputStream();
        //req.getReader();

        try {
            Item item = jsonToEntity(req);
            ItemService.saveItem(item);
            resp.getWriter().print("Item with id: " + item.getId() + " is saved");
        } catch (Exception e) {
            resp.getWriter().print("Error saving " + e.getMessage());
            e.printStackTrace();
        }

    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            Item item = jsonToEntity(req);
            item.setLastUpdatedDate(new Date());
            ItemService.updateItem(item);
            resp.getWriter().print("Item with id: " + item.getId() + " is updated");

        } catch (Exception e) {
            resp.getWriter().print("Error updating " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            long itemID = Long.parseLong(req.getParameter("id"));
            ItemService.deleteItem(itemID);
            resp.getWriter().print("item with id: " + itemID + " deleted");
        } catch (Exception e) {
            resp.getWriter().print("/item?id=should be a number");
            e.printStackTrace();
        }


    }

    private Item jsonToEntity(HttpServletRequest req) {

        StringBuilder stb = new StringBuilder();
        Gson gson = new GsonBuilder().setDateFormat("dd-MM-yy").create();
        String line;
        try (BufferedReader reader = req.getReader()) {


            while ((line = reader.readLine()) != null)
                stb.append(line);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return gson.fromJson(stb.toString(), Item.class);
    }

}