package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model){

        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Cheese Menus");
        return "menu/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String add(Model model){

        model.addAttribute(new Menu());
        model.addAttribute("title", "Create a Menu");

        return "menu/add";
    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Menu menu, Errors errors){

        if(!errors.hasErrors()){
            menuDao.save(menu);
            model.addAttribute("title", menu.getName() + " Menu");
            return "redirect:view/" + menu.getId();

        }

        model.addAttribute("title", "Create a Menu");
        model.addAttribute(menu);
        return "menu/add";

    }

    @RequestMapping(value="view/{menuId}")
    public String viewMenu(Model model, @PathVariable int menuId){
        Menu menu = menuDao.findOne(menuId);

        model.addAttribute("menu", menu);
        model.addAttribute("title", menu.getName());
        return "menu/view";
    }

    @RequestMapping(value="add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId){

        Menu menu = menuDao.findOne(menuId);

        model.addAttribute("form", new AddMenuItemForm(menu, cheeseDao.findAll()));
        model.addAttribute("title", "Add item to " + menu.getName());

        return "menu/add-item";
    }

    @RequestMapping(value="add-item", method = RequestMethod.POST)
    public String addItem(Model model, @Valid AddMenuItemForm form, Errors errors){

        if(!errors.hasErrors()){
            Cheese newCheese = cheeseDao.findOne(form.getCheeseId());
            Menu target = menuDao.findOne(form.getMenuId());
            target.addItem(newCheese);
            menuDao.save(target);
            return "redirect:view/" + target.getId();
        }
        model.addAttribute("error", errors);
        model.addAttribute("form", form);
        return"menu/add-item";
    }

}
