package org.itis.gr404.mvc.controller;

import org.itis.gr404.mvc.model.Brand;
import org.itis.gr404.mvc.model.Car;
import org.itis.gr404.mvc.service.BrandManager;
import org.itis.gr404.mvc.service.CarManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Регина on 08.04.2016.
 */
@Controller
@RequestMapping(value = "/new_car.html")
public class CarNewController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method = RequestMethod.GET)
    public Object initForm(ModelMap model) {
        Car defaultCar = new Car();
        defaultCar.setModel("new model");
        defaultCar.setPrice(new BigDecimal(15000));
        model.addAttribute("car", defaultCar);
        return "carNew";
    }

    @ModelAttribute("brandList")
    protected List<Brand> referenceData() {
        BrandManager brandManager = new BrandManager();
        return brandManager.getBrandList();
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields(new String[] {"brand"});
        Car car = (Car)binder.getTarget();
        BrandManager brandManager = new BrandManager();
        String brId = request.getParameter("brand");
        if (brId != null) {
            Long brandId = Long.parseLong(brId);
            Brand brand = brandManager.getBrandById(brandId);
            car.setBrand(brand);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("car") Car car){
        CarManager carManager = new CarManager();
        carManager.createCar(car);
        return "redirect:/list_cars.html";

    }
}


