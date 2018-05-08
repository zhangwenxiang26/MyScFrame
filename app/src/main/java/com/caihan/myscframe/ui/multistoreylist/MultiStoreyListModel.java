package com.caihan.myscframe.ui.multistoreylist;

import com.caihan.myscframe.ui.multistoreylist.bean.LocalActivityBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalData;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartBean;
import com.caihan.myscframe.ui.multistoreylist.bean.LocalShopCartButtomBean;
import com.caihan.myscframe.ui.multistoreylist.request.CartActivityItemBean;
import com.caihan.myscframe.ui.multistoreylist.request.CartItemBean;
import com.caihan.myscframe.ui.multistoreylist.request.RequestData;
import com.caihan.myscframe.ui.multistoreylist.request.ShopCartBean;
import com.caihan.scframe.framework.v1.support.MvpModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caihan
 * @date 2018/5/6
 * @e-mail 93234929@qq.com
 * 维护者
 */
public class MultiStoreyListModel implements MvpModel {

    private ArrayList<String> selectedGoodsList = new ArrayList<>();


    /**
     * 改变数据结构
     */
    public LocalData changeDataStructure(RequestData requestData) {
        LocalData localData = new LocalData();
        localData.setBusinessName(requestData.getBusinessName());
        localData.setIsCrossBorderBusiness(requestData.getIsCrossBorderBusiness());
        localData.setExemptionAmount(requestData.getExemptionAmount());
        localData.setMaxCrossBorderProductAmount(requestData.getMaxCrossBorderProductAmount());
        localData.setBusinessId(requestData.getBusinessId());
        List<LocalBean> localBeanList = new ArrayList<>();
        int cartItemTradeType;
        boolean isAllSelected = true;

        for (ShopCartBean shopCartBean : requestData.getShoppingCartList()) {
            LocalShopCartBean localShopCartBean = new LocalShopCartBean();
            localShopCartBean.setCartItemTradeType(shopCartBean.getCartItemTradeType());
            localShopCartBean.setCartItemTradeTypeTitle(shopCartBean.getCartItemTradeTypeTitle());
            localShopCartBean.setTaxTips(shopCartBean.getTaxTips());
            localShopCartBean.setItemTotalNum(shopCartBean.getItemTotalNum());
            localShopCartBean.setItemTotalAmount(shopCartBean.getItemTotalAmount());
            localShopCartBean.setTaxAmount(shopCartBean.getTaxAmount());
            localShopCartBean.setTotalAmount(shopCartBean.getTotalAmount());
            localShopCartBean.setBuyMultiItemTips(shopCartBean.getBuyMultiItemTips());
            localShopCartBean.setSaveAmount(shopCartBean.getSaveAmount());
            localBeanList.add(localShopCartBean);

            cartItemTradeType = localShopCartBean.getCartItemTradeType();

            for (CartActivityItemBean activityItemBean : shopCartBean.getCartActivityItemList()) {
                if ("1".equals(activityItemBean.getCartActivityItemType())) {
                    LocalActivityBean localActivityBean = new LocalActivityBean();
                    localActivityBean.setCartActivityItemType(activityItemBean.getCartActivityItemType());
                    localActivityBean.setCartActivityItemTypeId(activityItemBean.getCartActivityItemTypeId());
                    localActivityBean.setCartActivityItemTitle(activityItemBean.getCartActivityItemTitle());
                    localActivityBean.setCartActivityItemSubTitle(activityItemBean.getCartActivityItemSubTitle());
                    localActivityBean.setCartActivityItemTips(activityItemBean.getCartActivityItemTips());
                    localBeanList.add(localActivityBean);
                }

                for (CartItemBean cartItemBean : activityItemBean.getCartItemList()) {
                    cartItemBean.setCartItemTradeType(cartItemTradeType);
                    if ("0".equals(cartItemBean.getIsSelected())) {
                        //只要有一个未选中,就不是全选状态
                        isAllSelected = false;
                    }
                    localBeanList.add(cartItemBean);
                }
            }
            //保存全选状态
            localShopCartBean.setAllSelected(isAllSelected);

            LocalShopCartButtomBean localShopCartButtomBean = new LocalShopCartButtomBean();
            localShopCartButtomBean.setCartItemTradeType(shopCartBean.getCartItemTradeType());
            localShopCartButtomBean.setCartItemTradeTypeTitle(shopCartBean.getCartItemTradeTypeTitle());
            localShopCartButtomBean.setTaxTips(shopCartBean.getTaxTips());
            localShopCartButtomBean.setItemTotalNum(shopCartBean.getItemTotalNum());
            localShopCartButtomBean.setItemTotalAmount(shopCartBean.getItemTotalAmount());
            localShopCartButtomBean.setTaxAmount(shopCartBean.getTaxAmount());
            localShopCartButtomBean.setTotalAmount(shopCartBean.getTotalAmount());
            localShopCartButtomBean.setBuyMultiItemTips(shopCartBean.getBuyMultiItemTips());
            localShopCartButtomBean.setSaveAmount(shopCartBean.getSaveAmount());
            localBeanList.add(localShopCartButtomBean);
        }
        localData.setShoppingCartList(localBeanList);
        return localData;
    }

    @Override
    public void onDestroy() {

    }

    /**
     * 全选与非全选状态切换
     *
     * @param data
     * @param isSelected
     * @return true = 需要刷新数据,false = 无需刷新数据
     */
    public boolean changeAllSelected(List<LocalBean> data, boolean isSelected) {
        LocalShopCartBean localShopCartBean = null;
        int cartItemTradeType = -1;
        String isAllSelected = isSelected ? "1" : "0";
        for (LocalBean bean : data) {
            //如果数据返回无误的话,列表顺序是:1.有效商品,2.不支持商品,3.失效商品
            if (bean instanceof LocalShopCartBean) {
                cartItemTradeType = ((LocalShopCartBean) bean).getCartItemTradeType();
                /**
                 * 0-普通商品（包括完税商品）
                 * 1-海外直邮（BC）
                 * 2-海外直邮（个人）
                 * 3-保税商品
                 * 4-失效购物车商品（*排在最后、对应原来的storeCount字段与onSale字段，删除，下架，无货，关闭或者开启SKU
                 * 5-不支持快速配送/到店自提/快速送/次日达/扫码购）
                 */
                if (cartItemTradeType > 3) {
                    //不支持与失效商品无需判断,这里判断是否存在有效商品即可
                    return localShopCartBean != null;
                }
                localShopCartBean = (LocalShopCartBean) bean;
            } else if (bean instanceof CartItemBean) {
                //找到同业务类型的商品,设置选中状态,并且更新业务全选/反选状态
                if (((CartItemBean) bean).getCartItemTradeType() == cartItemTradeType) {
                    ((CartItemBean) bean).setIsSelected(isAllSelected);
                    if (localShopCartBean != null) {
                        localShopCartBean.setAllSelected(isSelected);
                    }
                }
            }
        }
        return localShopCartBean != null;
    }

    /**
     * 当选中一个商品的时候,判断是否需要联动头部全选按钮
     *
     * @return true = 全部刷新, false = 只刷新item
     */
    public boolean checkHaveAllSelected(List<LocalBean> data, CartItemBean cartItemBean) {
        int cartItemTradeType = cartItemBean.getCartItemTradeType();
        /**
         * 0-普通商品（包括完税商品）
         * 1-海外直邮（BC）
         * 2-海外直邮（个人）
         * 3-保税商品
         * 4-失效购物车商品（*排在最后、对应原来的storeCount字段与onSale字段，删除，下架，无货，关闭或者开启SKU
         * 5-不支持快速配送/到店自提/快速送/次日达/扫码购）
         */
        if (cartItemTradeType > 3) {
            //不支持与失效商品无需判断,这里判断是否存在有效商品即可
            return false;
        }
        LocalShopCartBean localShopCartBean = null;
        CartItemBean cartItem = null;
        boolean isAllSelected = true;
        for (LocalBean bean : data) {
            if (bean instanceof LocalShopCartBean) {
                if (cartItemTradeType > 3) {
                    //不支持与失效商品无需判断,这里判断是否存在有效商品即可
                    return false;
                }
                localShopCartBean = (LocalShopCartBean) bean;
                if (localShopCartBean.getCartItemTradeType() == cartItemTradeType) {
                    //先假设被全选
                    localShopCartBean.setAllSelected(isAllSelected);
                }else {
                    return localShopCartBean != null;
                }
            }else if (bean instanceof CartItemBean){
                cartItem = (CartItemBean) bean;
                if (cartItem.getCartItemTradeType() != cartItemTradeType){
                    return false;
                }
                //确保是同业务类型的商品
                if ("0".equals(cartItem.getIsSelected())){
                    //有一个未被选中,就取消全选
                    isAllSelected = false;
                    localShopCartBean.setAllSelected(isAllSelected);
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * 生成选中商品id列表
     *
     * @param data
     */
    public ArrayList<String> setSelectedGoodsList(List<LocalBean> data) {
        selectedGoodsList.clear();
        CartItemBean cartItemBean;
        for (LocalBean bean : data) {
            if (bean instanceof CartItemBean) {
                cartItemBean = (CartItemBean) bean;
                if (cartItemBean.getCartItemTradeType() > 3) {
                    return selectedGoodsList;
                }
                if ("1".equals(cartItemBean.getIsSelected())) {
                    selectedGoodsList.add(cartItemBean.getLocalItemId());
                }
            }
        }
        return selectedGoodsList;
    }

    /**
     * 添加选中的商品
     *
     * @param localItemId
     */
    public void addSelectedGoods(String localItemId) {
        if (!selectedGoodsList.contains(localItemId)) {
            selectedGoodsList.add(localItemId);
        }
    }

    public ArrayList<String> getSelectedGoodsList() {
        return selectedGoodsList;
    }
}