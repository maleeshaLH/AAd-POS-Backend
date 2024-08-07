package lk.ijse.aadpos_backend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.aadpos_backend.bo.ItemBo;
import lk.ijse.aadpos_backend.bo.custom.ItemBoImpl;
import lk.ijse.aadpos_backend.dto.ItemDto;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/item")
public class ItemController extends HttpServlet {


ItemBo itemBo = new ItemBoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() ==null || !req.getContentType().contains("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();


            ItemDto item = jsonb.fromJson(req.getReader(), ItemDto.class);
           // itemDto.setItemId(Util.idGenerate());

            try {
                if (itemBo.saveItem(item)){
                    resp.setStatus(HttpServletResponse.SC_OK);
                    writer.write("ok");
                }
                else {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    writer.write("error");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            resp.setStatus(HttpServletResponse.SC_OK);
        }catch (Exception e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getContentType() ==null || !req.getContentType().contains("application/json")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            ItemDto item = jsonb.fromJson(req.getReader(), ItemDto.class);

            if (itemBo.updateItem(item)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                    writer.write("Item Update Successful");
            }
            else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.write("Failed to update item");
            }

            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = req.getParameter("itemId");
        var writer = resp.getWriter();

        try {
            if (itemBo.deleteItem(Integer.parseInt(id))){
                resp.setStatus(HttpServletResponse.SC_OK);
                writer.write("Item Delete Successful");
            }else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.write("Failed to delete item");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
