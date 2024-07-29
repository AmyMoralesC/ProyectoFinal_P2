package com.cci.controller;

import com.cci.model.SubMuro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SubMuroController implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<SubMuro> subMurosList;
    private SubMuro selectedSubMuro;

    public SubMuroController() {
        subMurosList = new ArrayList<>();
        loadSampleData();  // Load some sample data
    }

    private void loadSampleData() {
        subMurosList.add(new SubMuro(1L, "Medicina", "Medicina", "podras encontrar informacion de medicin y mucho mas en este SubMuro"));
        subMurosList.add(new SubMuro(2L, "Ingeniería", "Ingeniería", "Pdras encontar infomacion valisosa dentro de este submuro de Ingeniería"));
        // Add more sample data as needed
    }

    public void openDialog(SubMuro subMuro) {
        this.selectedSubMuro = subMuro;
    }

    public List<SubMuro> getSubMurosList() {
        return subMurosList;
    }

    public SubMuro getSelectedSubMuro() {
        return selectedSubMuro;
    }

    public void clearSelectedSubMuro() {
        this.selectedSubMuro = null;
    }
}