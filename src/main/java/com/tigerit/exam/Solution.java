package com.tigerit.exam;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tigerit.exam.IO.*;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {
    @Override
    public void run() {
        // your application entry point

        // sample input process

        int T = readLineAsInteger();
        for (int i = 0; i < T; i++) {
            runTestCase(i+1);
        }
        String string = readLine();

        // sample output process
        printLine(string);
//        printLine(integer);
    }

    private void runTestCase(int testCase) {
        int nT = readLineAsInteger();
        TableModel[] tableArray = new TableModel[nT];
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < nT; i++) {
            tableArray[i] = readTableData();
            map.put(tableArray[i].getTableName(), i);
        }

        int nQ = readLineAsInteger();
        List<QueryModel> queryModelList = new ArrayList<>();
        for (int i = 0; i < nQ; i++) {
            queryModelList.add(readQuery(tableArray, map));
            readLine();
        }
        printLine("Test: "+testCase);
        printTestCase(queryModelList);
    }

    private void printTestCase(List<QueryModel> queryModelList) {
        for(QueryModel queryModel: queryModelList){
            printLine(queryModel.toString());
            printLine("");
        }

    }

    private QueryModel readQuery(TableModel[] tableArray, Map<String, Integer> tableNameMap) {
        //input query
        String selectClause = removeFirstWord(readLine());
        String fromClause = removeFirstWord(readLine());
        String joinClause = removeFirstWord(readLine());
        String onClause = removeFirstWord(readLine());

        //process from clause
        String table1Name = fromClause;
        if (fromClause.contains(" ")) {
            String[] splitFromClause = fromClause.split("\\s+");
            table1Name = splitFromClause[0];
        }

        TableModel table1 = tableArray[tableNameMap.get(table1Name)];

        //process join clause
        String table2Name = joinClause;
        if (joinClause.contains(" ")) {
            String[] splitJoinClause = joinClause.split("\\s+");
            table2Name = splitJoinClause[0];
        }

        TableModel table2 = tableArray[tableNameMap.get(table2Name)];

        //process select clause
        List<String> columnList = new ArrayList<>();
        if (selectClause.equals("*")) {
            columnList.addAll(Arrays.asList(table1.getColumnNames()));
            columnList.addAll(Arrays.asList(table2.getColumnNames()));
        } else {
            String[] splitSelectClause = selectClause.split(",");
            for (int i = 0; i < splitSelectClause.length; i++) {
                if (splitSelectClause[i].contains("\\."))
                    columnList.add(splitSelectClause[i].trim().split("\\.")[1]);
                else
                    columnList.add(splitSelectClause[i].trim());
            }
        }
        String[] selectedColumnArray = columnList.toArray(new String[columnList.size()]);
        Arrays.sort(selectedColumnArray);

        //process on clause
        String[] splitOnClause = onClause.split("=");
        String predicate1 = splitOnClause[0].trim().split("\\.")[1];
        String predicate2 = splitOnClause[1].trim().split("\\.")[1];

        //check query
       return getResultFromQuery(table1, table2, predicate1, predicate2, selectedColumnArray);

    }

    private QueryModel getResultFromQuery(TableModel table1, TableModel table2, String predicate1, String predicate2,
                                          String[] selectedColumnArray) {
        int table1Index = table1.getColumnMap().get(predicate1);
        int table2Index = table2.getColumnMap().get(predicate2);

        List<int[]> queryResults = new ArrayList<>();

        for (int i = 0; i < table1.getnD(); i++) {
            for (int j = 0; j < table2.getnD(); j++) {
                if (table1.getTableData()[i][table1Index] == table1.getTableData()
                        [j][table2Index]) {
                    int[] resultRow = new int[selectedColumnArray.length];
                    Map<String, Integer> map1 = table1.getColumnMap();
                    Map<String, Integer> map2 = table2.getColumnMap();
                    for(int k=0; k<selectedColumnArray.length; k++){
                        if(map1.containsKey(selectedColumnArray[k])){
                            resultRow[k] = table1.getTableData()[i][map1.get(selectedColumnArray[k])];
                        }else if(map2.containsKey(selectedColumnArray[k])){
                            resultRow[k] = table2.getTableData()[j][map2.get(selectedColumnArray[k])];
                        }
                    }
                    queryResults.add(resultRow);

                }
            }
        }
        return new QueryModel(selectedColumnArray, queryResults);

    }

    private TableModel readTableData() {
        String tableName = readLine();
        String[] columnRecordValues = readLine().trim().split("\\s+");
        int nC = Integer.parseInt(columnRecordValues[0]);
        int nD = Integer.parseInt(columnRecordValues[1]);
        String[] tableColumnNames = readLine().trim().split("\\s+");
        int[][] tableData = new int[nD][nC];
        for (int i = 0; i < nD; i++) {
            String[] rowData = readLine().split("\\s+");
            for (int j = 0; j < nC; j++) {
                tableData[i][j] = Integer.parseInt(rowData[j]);
            }
        }
        return new TableModel(tableName, nC, nD, tableColumnNames, tableData);
    }
}
