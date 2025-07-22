package controller;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.dao.CategoryDAO;
import model.dao.MenuDAO;
import model.dto.CategoryDTO;
import model.dto.MenuDTO;
import utils.AuthUtils;
import utils.CONSTANT;
@WebServlet(name = "MenuController", urlPatterns = {"/MenuController"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 1024 * 1024 * 5,   // 5MB
    maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class MenuController extends HttpServlet {

    MenuDAO mdao = new MenuDAO();
    CategoryDAO cdao = new CategoryDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String url = "";
        try {
            String action = request.getParameter("action");
            System.out.println(">>> MenuController: processRequest reached");
            System.out.println(">>> getParameter('action') = " + action);

            // Fallback for multipart/form-data
            if (action == null && request.getContentType() != null &&
                request.getContentType().toLowerCase().startsWith("multipart/")) {
                action = getPartValue(request, "action");
            }
            System.out.println(">>> MenuController: action = " + action);
            if ("addMenu".equals(action)) {
                url = MenuAddingCenter(request, response);
            } else if ("searchMenu".equals(action)) {
                url = MenuSearchingCenter(request, response);
            } else if ("editMenu".equals(action)) {
                url = MenuEditingCenter(request, response);
            } else if ("updateMenu".equals(action)) {
                System.out.println(">>> action = " + action);

                url = MenuUpdatingCenter(request, response);
            } else if ("deleteMenu".equals(action)) {
                url = MenuDeletingCenter(request, response);
            } else if ("loadAllMenu".equals(action)) {
                url = LoadAllMenuCenter(request, response);
            }
            if ("testUpload".equals(action)) {
                url = testUpload(request, response);
            }


        } catch (Exception e) {
            e.printStackTrace();
            url = "error.jsp";
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    
    private String testUpload(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String food = getPartValue(request, "food");
    Part imagePart = request.getPart("FoodImage");

    System.out.println(">> Food: " + food);
    System.out.println(">> Image: " + (imagePart != null ? imagePart.getSubmittedFileName() : "null"));

    request.setAttribute("message", "Upload received. Check console.");
    return "uploadTest.jsp"; // Show message back on the same page
}

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override public String getServletInfo() {
        return "Menu Controller for adding, editing, deleting menu items";
    }

    // === Center Methods ===

    private String LoadAllMenuCenter(HttpServletRequest request, HttpServletResponse response) {
        List<MenuDTO> list = mdao.getAllMenus();
        List<CategoryDTO> categories = cdao.getAllCategories();
        request.setAttribute("list", list);
        request.setAttribute("categories", categories);
        request.setAttribute("keyword", "");
        request.setAttribute("selectedCategoryId", "");
        return "welcome.jsp";
    }

    private String MenuSearchingCenter(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyword");
        String categoryId = request.getParameter("categoryId");
        List<MenuDTO> list;

        if (categoryId != null && !categoryId.trim().isEmpty() && !categoryId.equals("0")) {
            int catId = Integer.parseInt(categoryId);
            if (keyword != null && !keyword.trim().isEmpty()) {
                list = mdao.getMenusByCategoryAndKeyword(catId, keyword.trim());
            } else {
                list = mdao.getMenuByCategory(catId);
            }
        } else {
            list = (keyword != null && !keyword.trim().isEmpty())
                    ? mdao.getMenuByName(keyword.trim())
                    : mdao.getAllMenus();
        }

        List<CategoryDTO> categories = cdao.getAllCategories();
        request.setAttribute("menuList", list);
        request.setAttribute("categoryList", categories);
        request.setAttribute("keyword", keyword);
        request.setAttribute("selectedCategoryId", categoryId);
        return "shop.jsp";
    }

    private String MenuAddingCenter(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (AuthUtils.isAdmin(request)) {
            String checkError = "";
            String message = "";

            String menuId = getPartValue(request, "menuId");
            String food = getPartValue(request, "food");
            System.out.println("FOOD: " + food);

            String price = getPartValue(request, "price");
            String food_description = getPartValue(request, "food_description");
            String food_status = getPartValue(request, "food_status");
            String categoryId = getPartValue(request, "categoryId");

            int menu_id_value = parseInt(menuId);
            int category_id_value = parseInt(categoryId);
            BigDecimal price_value = parseDecimal(price);

            // Image Upload
            String imagePath = "";
            Part imagePart = request.getPart("FoodImage");
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            if (fileName != null && !fileName.isEmpty()) {
                String uploadPath = getServletContext().getRealPath("/") + "images" + File.separator + "food";
                new File(uploadPath).mkdirs();
                File file = new File(uploadPath, fileName);
                imagePart.write(file.getAbsolutePath());
                imagePath = "images/food/" + fileName;
            }
            System.out.println("Parsed Menu ID: " + menu_id_value);
System.out.println("Parsed Category ID: " + category_id_value);

            if (mdao.isMenuExists(menu_id_value)) {
                checkError = "Menu ID already exists.";
            }

            MenuDTO menu = new MenuDTO(menu_id_value, food, imagePath, price_value, food_description, food_status, category_id_value);
            if (checkError.isEmpty()) {
                if (!mdao.create(menu)) {
                    checkError = "Cannot add new menu.";
                } else {
                    message = "Add Menu Successfully.";
                }
            }

            request.setAttribute("menu", menu);
            request.setAttribute("checkError", checkError);
            request.setAttribute("message", message);
        }
        return "menuForm.jsp";
    }

    private String MenuEditingCenter(HttpServletRequest request, HttpServletResponse response) {
        String menuId = request.getParameter("menuId");
        String keyword = request.getParameter("keyword");
        int menu_id_value = parseInt(menuId);

        if (AuthUtils.isAdmin(request)) {
            MenuDTO menu = mdao.getMenuByID(menu_id_value);
            if (menu != null) {
                request.setAttribute("keyword", keyword);
                request.setAttribute("menu", menu);
                request.setAttribute("isEdit", true);
                return "menuForm.jsp";
            }
        }
        return MenuSearchingCenter(request, response);
    }

    private String MenuUpdatingCenter(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (AuthUtils.isAdmin(request)) {
            String checkError = "";
            String message = "";

            String menuId = getPartValue(request, "menuId");
            String food = getPartValue(request, "food");
            String price = getPartValue(request, "price");
            String food_description = getPartValue(request, "food_description");
            String food_status = getPartValue(request, "food_status");
            String categoryId = getPartValue(request, "categoryId");
            String currentImage = getPartValue(request, "currentImage");

            int menu_id_value = parseInt(menuId);
            int category_id_value = parseInt(categoryId);
            BigDecimal price_value = parseDecimal(price);

            // Handle image
            String imagePath = currentImage;
            Part imagePart = request.getPart("FoodImage");
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            if (fileName != null && !fileName.isEmpty()) {
                String uploadPath = utils.CONSTANT.savedFoodImagePath;
                new File(uploadPath).mkdirs();
                File file = new File(uploadPath, fileName);
                imagePart.write(file.getAbsolutePath());
                imagePath = "images/food/" + fileName;
                System.out.println(uploadPath);
            }

            if (food == null || food.trim().isEmpty()) {
                checkError += "Food is required.<br/>";
            }
            if (price_value.compareTo(BigDecimal.ZERO) <= 0) {
                checkError += "Price must be greater than 0.<br/>";
            }

            MenuDTO menu = new MenuDTO(menu_id_value, food, imagePath, price_value, food_description, food_status, category_id_value);
            if (checkError.isEmpty()) {
                if (mdao.update(menu)) {
                    message = "Menu updated successfully.";
                    return MenuSearchingCenter(request, response);
                } else {
                    checkError += "Cannot update menu.<br/>";
                }
            }

            request.setAttribute("menu", menu);
            request.setAttribute("checkError", checkError);
            request.setAttribute("message", message);
            request.setAttribute("isEdit", true);
        }
        return "menuForm.jsp";
    }

    private String MenuDeletingCenter(HttpServletRequest request, HttpServletResponse response) {
        String menuId = request.getParameter("menuId");
        if (AuthUtils.isAdmin(request)) {
            mdao.delete(menuId);
        }
        return MenuSearchingCenter(request, response);
    }

    // === Utilities ===

    private String getPartValue(HttpServletRequest request, String fieldName) throws IOException, ServletException {
        Part part = request.getPart(fieldName);
        if (part == null) return null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n")).trim();
        }
    }

    private int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    private BigDecimal parseDecimal(String s) {
        try {
            return new BigDecimal(s);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}
