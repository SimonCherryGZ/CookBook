package com.simoncherry.cookbook.model;

import java.util.List;

/**
 * Created by Simon on 2017/3/28.
 */

public class MobRecipeResult {

    private int curPage;
    private int total;
    private List<MobRecipe> list;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MobRecipe> getList() {
        return list;
    }

    public void setList(List<MobRecipe> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MobRecipeResult{" +
                "curPage=" + curPage +
                ", total=" + total +
                ", list=" + list +
                '}';
    }
}
