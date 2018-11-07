package servlets.s.comments.my_service;

import org.json.JSONArray;
import org.json.JSONObject;
import spring.entity.EntityComment;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentServletService {

    public JSONArray getCommentsArrayJ(List<EntityComment> listComments) {

        if (listComments == null || listComments.size() == 0) {

            return null;
        }

        JSONArray commentsArrayJ = new JSONArray();

        for (EntityComment comment : listComments) {

            JSONObject commentJ = new JSONObject(comment);

            commentJ.remove("shopId");

            commentJ.remove("time");

            commentJ.put("time",comment.getTime().getTime());

            commentsArrayJ.put(commentJ);
        }

        return commentsArrayJ;
    }

    //Метод для поиска пробелов в строке

    public boolean checkString(String text) {

       // regex = Регулярное выражение для проверки строки

        String regex = "^\\s*$"; // regex для поиска пробелов в строке

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }
}

// ^ - начало привязки строки
// $ - конец привязки строки
// \s - класс символов пробелов
// * имеет нулевое или большее количество повторений
// В многострочном режиме ^ и $ также соответствуют началу и концу строки
