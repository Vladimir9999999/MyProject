package utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Iterator;

public class FilesUtil {

    private static String imageName = "logo";

    public static boolean saveLogo(Part filePart, String path) {

        if(filePart != null) {

             File shopPage = new File(path);

             if (!shopPage.exists())
                 if (!shopPage.mkdirs()) {
                     System.out.println("не удалось создать файл");
                     return false;
                 }
             String mem = Paths.get(filePart.getSubmittedFileName())
                     .getFileName()
                     .toString()
                     .split("\\.")[1];

             File logo = new File(path+"/"+imageName+".jpg");

             try {

                 ImageIO.write(resize(filePart,mem),"jpg",logo);
                return true;

             } catch (IOException e) {

                 e.printStackTrace();

             }

         }
         return false;
     }
    public static boolean saveLogo(Part filepart,String path, String name){

      imageName = name;
      return saveLogo(filepart, path);

    }
    public static boolean saveImage(String url, String filePath){

        File img = new File(filePath);
        if(img.exists()){
            return false;
        }
        try {

            BufferedImage image = resize(url);
            if(image == null){
                return false;
            }
            ImageIO.write(image,"jpg", new File(filePath));
            return true;

        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }

    }


    public static String getImageName() {
        return imageName;
    }

    public static void setImageName(String imageName) {
        FilesUtil.imageName = imageName;
    }

    private static BufferedImage resize(String url) throws IOException ,IllegalArgumentException{
        float mediumSize = 300*1028;

        try{
            BufferedImage img = ImageIO.read(new URL(url));
            float size;
            if(img == null){
                System.out.println( "AHTUNG битая картинка: " + url);
                return null;
            }
            img = resizeImageWithHint(img,img.getWidth(),img.getHeight());

            size = imageSize(img,"jpg");

            if(size>mediumSize) {

                float delimiter = mediumSize / size;

                int newH = Math.round(img.getHeight() * delimiter);
                int newW = Math.round(img.getWidth() * delimiter);

                img = resizeImageWithHint(img, newW, newH);
            }

            return img;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }

    private static BufferedImage resize(Part filePart, String format) throws IOException {

        float mediumSize = 300*1028;


            BufferedImage img = ImageIO.read(filePart.getInputStream());
            float size;

            img = resizeImageWithHint(img,img.getWidth(),img.getHeight());

            size = imageSize(img,format);

            if(size>mediumSize) {

                float delimiter = mediumSize / size;

                int newH = Math.round(img.getHeight() * delimiter);
                int newW = Math.round(img.getWidth() * delimiter);

                img = resizeImageWithHint(img, newW, newH);
            }

            return img;


    }



    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int w, int h){

        BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = resizedImage.createGraphics();

        g.drawImage(originalImage, 0, 0, w, h, Color.WHITE,null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    private static long imageSize (BufferedImage img, String format){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {

            ImageIO.write(img,format , bos);
            return bos.toByteArray().length;

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

    }
    public static Dimension getImageDim(final String path) {
        Dimension result = null;
        String suffix = getFileSuffix(path);
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        if (iter.hasNext()) {
            ImageReader reader = iter.next();
            try {
                ImageInputStream stream = new FileImageInputStream(new File(path));
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                result = new Dimension(width, height);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                reader.dispose();
            }
        } else {
            System.out.println("No reader found for given format: " + suffix);
        }
        return result;
    }

    private static String getFileSuffix(final String path) {
        String result = null;
        if (path != null) {
            result = "";
            if (path.lastIndexOf('.') != -1) {
                result = path.substring(path.lastIndexOf('.'));
                if (result.startsWith(".")) {
                    result = result.substring(1);
                }
            }
        }
        return result;
    }
}
