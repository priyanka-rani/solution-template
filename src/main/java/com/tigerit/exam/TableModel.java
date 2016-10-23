package com.tigerit.exam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Priyanka on 10/24/2016.
 */

public class TableModel {
    private String tableName;
    private int nC;
    private int nD;
    private String[] columnNames;
    private int[][] tableData;
    private Map<String, Integer> columnMap = new HashMap<>();

    public TableModel(String tableName, int nC, int nD,
                      String[] columnNames, int[][] tableData) {
        this.tableName = tableName;
        this.nC = nC;
        this.nD = nD;
        this.columnNames = columnNames;
        this.tableData = tableData;
        for(int i = 0; i< nC; i++){
            columnMap.put(columnNames[i], i);
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getnC() {
        return nC;
    }

    public void setnC(int nC) {
        this.nC = nC;
    }

    public int getnD() {
        return nD;
    }

    public void setnD(int nD) {
        this.nD = nD;
    }

    public int[][] getTableData() {
        return tableData;
    }

    public void setTableData(int[][] tableData) {
        this.tableData = tableData;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public Map<String, Integer> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<String, Integer> columnMap) {
        this.columnMap = columnMap;
    }
}
