package utils;

import Models.CategoryYML;
import Models.ProductYML;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exel {

    public List<CategoryYML> readFile(){
        try {
            FileInputStream inputStream = new FileInputStream(System.getProperty("jboss.home.dir")+"/tst.xlsx");

        XSSFWorkbook excelBook = new XSSFWorkbook(inputStream);
        XSSFSheet excelShet = excelBook.getSheet("Лист1");

        XSSFRow exelRow;


        List<CategoryYML> categoryYMLList = new ArrayList<>();

        for (int i = 1;(exelRow = excelShet.getRow(i))!=null; i++) {




            List<Cell> cells = Lists.newArrayList(exelRow.cellIterator());

            boolean content = cells.size()>1 &&(cells.get(0).getCellTypeEnum()== CellType.NUMERIC || cells.get(0).getCellTypeEnum() == CellType.FORMULA);
                if (content){

                    CategoryYML categoryYML = new CategoryYML();
                    categoryYML.setName(cells.get(1).toString());
                    int index = categoryYMLList.indexOf(categoryYML);

                    if(index < 0){

                        categoryYMLList.add(categoryYML);
                        categoryYML.setId(categoryYMLList.size());
                        List<ProductYML> productYMLS  = new ArrayList<>();

                        categoryYML.setProductsYML(productYMLS);

                    }

                    index = categoryYMLList.indexOf(categoryYML);

                    ProductYML productYML = new ProductYML();
                    productYML.setName(cells.get(2).toString());
                    productYML.setDescription(cells.get(3).toString());
                    productYML.setPrice((float) cells.get(4).getNumericCellValue());
                    productYML.setUrlPicture(cells.get(6).toString());

                    if(cells.size()>=7 && cells.get(5)!=null && !cells.get(5).toString().equals("")){

                        productYML.setName(productYML.getName()+ " 30см.");

                        categoryYMLList.get(index).getProductsYML().add(productYML);

                        productYML = new ProductYML();

                        productYML.setUrlPicture(cells.get(6).toString());
                        productYML.setName(cells.get(2).toString()+ " 40см.");
                        productYML.setDescription(cells.get(3).toString());
                        productYML.setPrice((float) cells.get(5).getNumericCellValue());

                    }

                    categoryYMLList.get(index).getProductsYML().add(productYML);


                    //System.out.println(cells);

                }

        }

            System.out.println(categoryYMLList);
            return categoryYMLList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
