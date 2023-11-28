package net.leaderos.plugin.modules.webstore.gui;

import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import net.leaderos.plugin.api.handlers.UpdateCacheEvent;
import net.leaderos.plugin.api.managers.ModuleManager;
import net.leaderos.plugin.helpers.ChatUtil;
import net.leaderos.plugin.modules.cache.model.User;
import net.leaderos.plugin.modules.webstore.model.Category;
import net.leaderos.plugin.Bukkit;
import net.leaderos.plugin.modules.webstore.model.Product;
import net.leaderos.plugin.helpers.GuiHelper;
import net.leaderos.plugin.helpers.MDChat.MDChatAPI;
import net.leaderos.shared.model.Response;
import net.leaderos.shared.model.request.PostRequest;
import net.leaderos.shared.modules.auth.AuthHelper;
import net.leaderos.shared.modules.credit.enums.UpdateType;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.*;

/**
 * Webstore gui
 * @author poyrazinan
 * @since 1.0
 */
public class WebStoreGui {

    /**
     * Constructor of gui
     */
    public WebStoreGui() {}

    /**
     * Opens gui to player
     * @param player to show gui
     * @param categoryObj category to open
     */
    public static void showGui(Player player, Category categoryObj) {
        // Gui template as array
        String[] layout = Bukkit.getInstance().getModulesFile().getWebStore().getGui().getLayout().toArray(new String[0]);
        // Inventory object
        String guiName = ChatUtil.replacePlaceholders(Bukkit.getInstance().getLangFile().getGui().getWebStoreGui().getGuiName());
        InventoryGui gui = new InventoryGui(Bukkit.getInstance(), null, guiName, layout);
        // Filler item for empty slots
        gui.setFiller(GuiHelper.getFiller(Bukkit.getInstance().getModulesFile().getWebStore().getGui().getFillerItem().isUseFiller(), Bukkit.getInstance().getModulesFile().getWebStore().getGui().getFillerItem().getMaterial()));

        // List creator
        List<Category> categoryList;
        List<Product> productList;
        if (categoryObj == null) {
            categoryList = Category.getCategories();
            productList = new ArrayList<>();
        } else {
            categoryList = categoryObj.getSubCategories();
            productList = categoryObj.getProductList();
        }

        GuiElementGroup elementGroup = new GuiElementGroup('e');
        // Category group creator
        GuiElementGroup categoryGroup = new GuiElementGroup('c');
        if (!categoryList.isEmpty()) {
            // Element group
            addCategoriesToGroup(categoryList, elementGroup, player);

            // Category group
            addCategoriesToGroup(categoryList, categoryGroup, player);
            categoryGroup.setFiller(GuiHelper.getFiller(Bukkit.getInstance().getModulesFile().getWebStore().getGui().getFillerItem().isUseFiller(), Bukkit.getInstance().getModulesFile().getWebStore().getGui().getFillerItem().getMaterial()));
            gui.addElement(categoryGroup);
        }
        // Product group creator
        GuiElementGroup productGroup = new GuiElementGroup('p');
        if (!productList.isEmpty()) {
            // Element group
            addProductsToGroup(productList, elementGroup, player, gui);

            // Product Group
            addProductsToGroup(productList, productGroup, player, gui);
            productGroup.setFiller(GuiHelper.getFiller(Bukkit.getInstance().getModulesFile().getWebStore().getGui().getFillerItem().isUseFiller(), Bukkit.getInstance().getModulesFile().getWebStore().getGui().getFillerItem().getMaterial()));
            gui.addElement(productGroup);
        }
        elementGroup.setFiller(GuiHelper.getFiller(Bukkit.getInstance().getModulesFile().getWebStore().getGui().getFillerItem().isUseFiller(), Bukkit.getInstance().getModulesFile().getWebStore().getGui().getFillerItem().getMaterial()));
        gui.addElement(elementGroup);


        // Next and previous page icons
        gui.addElement(GuiHelper.createNextPage(Bukkit.getInstance().getModulesFile().getWebStore().getGui().getNextPage().getItem()));
        gui.addElement(GuiHelper.createPreviousPage(Bukkit.getInstance().getModulesFile().getWebStore().getGui().getPreviousPage().getItem()));
        gui.show(player);
    }

    /**
     * Adds product to gui element group
     * @param productList list of product
     * @param group element group of gui
     * @param player inventory holder
     * @param gui opened gui
     */
    private static void addProductsToGroup(List<Product> productList, GuiElementGroup group, Player player, InventoryGui gui) {
        productList.forEach(product -> group.addElement(new DynamicGuiElement('e', (viewer)
                -> new StaticGuiElement('e',
                product.getProductIcon(),
                1,
                click -> {
                    click.getEvent().setCancelled(true);
                    gui.close();
                    if (User.isPlayerAuthed(player)) {
                        User user = User.getUser(player.getName());
                        Map<String, String> body = new HashMap<>();
                        body.put("user", user.getId());
                        body.put("products[0][id]", product.getProductId());
                        body.put("products[0][quantity]", "1");
                        // Titles
                        String title = ChatUtil.color(Bukkit.getInstance().getLangFile().getGui().getWebStoreGui().getBuyWebStoreTitle());
                        String subtitleError = ChatUtil.color(Bukkit.getInstance().getLangFile().getGui().getWebStoreGui().getBuyWebStoreError());
                        String subtitleProgress = ChatUtil.color(Bukkit.getInstance().getLangFile().getGui().getWebStoreGui().getBuyWebStoreProgress());
                        String subtitleSuccess = ChatUtil.color(Bukkit.getInstance().getLangFile().getGui().getWebStoreGui().getBuyWebStoreSuccess());
                        String subtitleNotEnoughCredit = ChatUtil.color(Bukkit.getInstance().getLangFile().getGui().getWebStoreGui().getBuyWebStoreNotEnoughCredit());
                        player.sendTitle(title, subtitleProgress);
                        // Buy progress
                        try {
                            Response buyRequest = new PostRequest("store/buy", body).getResponse();
                            if (buyRequest.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                // Calls UpdateCache event for update player's cache
                                org.bukkit.Bukkit.getPluginManager().callEvent(new UpdateCacheEvent(player.getName(), product.getPrice(), UpdateType.REMOVE));
                                player.sendTitle(title, subtitleSuccess);
                            }
                            else if (buyRequest.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST)
                                player.sendTitle(title, subtitleNotEnoughCredit);
                            else throw new IOException();
                        } catch (IOException e) {
                            player.sendTitle(title, subtitleError);
                        }
                    }
                    else {
                        // If auth login is enabled
                        if (ModuleManager.getModuleStatus("Auth")) {
                            String authLink = AuthHelper.getAuthLink(player.getName(), player.getUniqueId());
                            if (authLink != null)
                                player.spigot().sendMessage(
                                        MDChatAPI.getFormattedMessage(
                                                ChatUtil.color(Bukkit.getInstance().getLangFile().getMessages().getAuth().getModuleError()
                                                        .replace("%link%", authLink)
                                                        .replace("{prefix}", Bukkit.getInstance().getLangFile().getMessages().getPrefix()))));
                        }
                        // If cache not found and Auth is disabled situation
                        else
                            ChatUtil.sendMessage(player, Bukkit.getInstance().getLangFile().getGui().getWebStoreGui().getBuyWebStoreNoAuthLinkError());
                    }
                    return false;
                })))
        );
    }

    /**
     * Adds category object to element group
     * @param categoryList list of category
     * @param group element group
     * @param player gui opener
     */
    private static void addCategoriesToGroup(List<Category> categoryList, GuiElementGroup group, Player player) {
        categoryList.forEach(category -> group.addElement(new DynamicGuiElement('s', (viewer)
                -> new StaticGuiElement('s',
                category.getCategoryIcon(),
                1,
                click -> {
                    click.getEvent().setCancelled(true);
                    showGui(player, category);
                    return true;
                })))
        );
    }
}
