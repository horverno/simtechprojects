/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.puff.diffsolver.output;

import hu.puff.diffsolver.input.InputData;
import java.util.List;

/**
 *
 * @author JombY
 */
public class SaveFile {

    InputData data;
    List<MeasurementItem> list;

    public SaveFile() {
    }

    public SaveFile(InputData data, List<MeasurementItem> list) {
        this.data = data;
        this.list = list;
    }

    public InputData getData() {
        return data;
    }

    public void setData(InputData data) {
        this.data = data;
    }

    public List<MeasurementItem> getList() {
        return list;
    }

    public void setList(List<MeasurementItem> list) {
        this.list = list;
    }

}
