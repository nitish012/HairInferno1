package com.example.hairinferno1.Modal;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleHome {

    @SerializedName("CODE")
    @Expose
    private Integer cODE;
    @SerializedName("NEXT_PAGE")
    @Expose
    private Integer nEXTPAGE;
    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("RESULT")
    @Expose
    private List<RESULT> rESULT = null;

    public Integer getCODE() {
        return cODE;
    }

    public void setCODE(Integer cODE) {
        this.cODE = cODE;
    }

    public Integer getNEXTPAGE() {
        return nEXTPAGE;
    }

    public void setNEXTPAGE(Integer nEXTPAGE) {
        this.nEXTPAGE = nEXTPAGE;
    }

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    public List<RESULT> getRESULT() {
        return rESULT;
    }

    public void setRESULT(List<RESULT> rESULT) {
        this.rESULT = rESULT;
    }

}