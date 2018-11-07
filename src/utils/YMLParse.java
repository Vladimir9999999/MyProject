package utils;

import Models.CategoryYML;
import Models.ProductYML;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class YMLParse {

    private final String URL ;

    public YMLParse(String uri) {
        URL = uri;
    }

    private List<ProductYML> productsYML;

    public List<CategoryYML> parse() {

        try {


            HtmlLoader loader = new HtmlLoader();
            loader.loadFileYML(URL,"shops.xml");

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();


            Document document = documentBuilder.parse(new File("shops.xml"));

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();


            productsYML = parseProduct(xPath,document);

            return parseCategory(xPath,document);



        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<CategoryYML> parseCategory(XPath xPath, Document document){

        XPathExpression expression;
        List<CategoryYML> categoriesYML = null;
        try {
            expression = xPath.compile("//categories/category");


        NodeList categories = (NodeList) expression.evaluate(document,XPathConstants.NODESET);
        categoriesYML = new ArrayList<>(categories.getLength());

        for (int i = 0; i<categories.getLength(); i++){

            Node cat = categories.item(i);
            NamedNodeMap attributes = cat.getAttributes();

            long categoryId = Long.parseLong(attributes.getNamedItem("id").getNodeValue());
            Node parentNode =  attributes.getNamedItem("parentId");
            long parentCat;

            if(parentNode == null){

               parentCat = 0;

            }else {

                parentCat = Long.parseLong(parentNode.getNodeValue());

            }

            String name = cat.getTextContent();

            List<ProductYML> catProd = new ArrayList<>();
            for (ProductYML productYML : productsYML){
                if(productYML.getCategory() == categoryId){
                    catProd.add(productYML);
                }
            }

            System.out.println("id:" + categoryId + " name:" + name + " parent" + parentCat);

            CategoryYML categoryYML = new CategoryYML();
            categoryYML.setProductsYML(catProd);
            categoryYML.setId(categoryId);
            categoryYML.setParent(parentCat);
            categoryYML.setName(name);
            categoriesYML.add(categoryYML);

        }

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return categoriesYML;
    }

    private List<ProductYML> parseProduct(XPath xPath, Document document){

        XPathExpression expProd = null;
        List <ProductYML> productsYML = null;
        try {
            expProd = xPath.compile("//offers/offer");


        NodeList products = (NodeList) expProd.evaluate(document,XPathConstants.NODESET);
        productsYML= new ArrayList<>(products.getLength());

        for (int i=0; i<products.getLength(); i++){

            Node product = products.item(i);
            if(product.getNodeType() == Node.ELEMENT_NODE){
                ProductYML productYML = new ProductYML();
                Element element  = (Element) product;
                float price = Float.parseFloat(getParemeter(element,"price"));
                long category = Long.parseLong(getParemeter(element,"categoryId"));
                String picture = getParemeter(element,"picture");
                String name = getParemeter(element,"name");
                String description = getParemeter(element,"description");

                productYML.setCategory(category);
                productYML.setDescription(description);
                productYML.setCategory(category);
                productYML.setName(name);
                productYML.setPrice(price);
                productYML.setUrlPicture(picture);

                //System.out.println(i+"|"+name+"|"+price+"|"+category+"| "+picture+" |"+description);
                productsYML.add(productYML);
            }
        }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return productsYML;
    }



    private String getParemeter(Element e, String domm){
        return e.getElementsByTagName(domm).item(0).getTextContent();
    }

}
