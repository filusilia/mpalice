package com.alice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alice.entity.Address;
import com.alice.service.AddressService;

/**
 * 地址Controller
 */
@RestController
@RequestMapping("address")
public class AddressController {

    public static Logger logger = Logger.getLogger(AddressController.class);

    /**
     * 地址服务
     */
    @Autowired
    private AddressService addressService;

    /**
     * 获取地址列表
     *
     * @param userId 成员id
     */
    @RequestMapping("address_list.jhtml")
    public ModelAndView getAddressList(Integer userId) {
        List<Address> addressList = addressService.getAddressList(userId);
        ModelAndView mv = new ModelAndView();

        mv.addObject("addresslist", addressList);
        mv.setViewName("address/noaddress");
        return mv;
    }

    /**
     * 跳转至添加界面
     */
    @RequestMapping("add_address.jhtml")
    public ModelAndView addAddress(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        String userId = request.getParameter("userId");
        if (!StringUtils.isBlank(userId)) {
            mv.addObject("userId", Integer.valueOf(userId));
        }
        mv.setViewName("address/add_address");
        return mv;
    }

    /**
     * 地址添加至数据库
     *
     * @param address 地址实体
     */
    @ResponseBody
    @RequestMapping("save_address.html")
    public String saveAddress(Address address) {

        logger.debug("saveAddress method just run success.");
        Map<String,Object> resultMap = new HashMap<>();
        Gson gson = new Gson();
        Boolean success = addressService.addAddress(address);
        String msg = "some msg from saveAddress";

        resultMap.put("success", success);
        resultMap.put("msg", msg);
        return gson.toJson(resultMap);
    }

    /**
     * 删除地址
     *
     * @param addressId 地址id
     */
    @ResponseBody
    @RequestMapping("delete_address.html")
    public String deleteAddress(Integer addressId) {
        logger.debug("deleteAddress method just run success.");

        Map<String,Object> resultMap = new HashMap<>();
        Gson gson = new Gson();
        Boolean success = addressService.deleteAddress(addressId);
        String msg = "some msg from deleteAddress";

        resultMap.put("success", success);
        resultMap.put("msg", msg);
        return gson.toJson(resultMap);
    }

    /**
     * 更新地址
     *
     * @param address 地址实体
     */
    @ResponseBody
    @RequestMapping("update_address.html")
    public String updateAddress(Address address) {

        logger.debug("updateAddress method just run success.");

        Map<String,Object> resultMap = new HashMap<>();
        Gson gson = new Gson();
        Boolean success = addressService.updateAddress(address);
        String msg = "some msg from updateAddress";

        resultMap.put("success", success);
        resultMap.put("msg", msg);
        return gson.toJson(resultMap);

    }
}
