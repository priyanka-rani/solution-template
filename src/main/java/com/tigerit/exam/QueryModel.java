package com.tigerit.exam;

import java.util.List;

/**
 * Created by Priyanka on 10/24/2016.
 */

public class QueryModel {
    private String[] selectedColumns;
    private List<int[]> results;

    public QueryModel(String[] selectedColumns, List<int[]> results) {
        this.selectedColumns = selectedColumns;
        this.results = results;
    }

    public String[] getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(String[] selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public List<int[]> getResults() {
        return results;
    }

    public void setResults(List<int[]> results) {
        this.results = results;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String column : selectedColumns){
            sb.append(column+" ");
        }
        for(int[] rows: results){
            sb.append("\n");
            for(int rowValue : rows){
                sb.append(rowValue+" ");
            }
        }
        return sb.toString();
    }
}
