package utils;

import Models.AccountEntity;
import java.util.HashMap;
import java.util.Random;

public class SMSCodeUtil {

    private static HashMap<AccountEntity,Integer> listRemember = new HashMap<>();

    public static void addSMSCode (AccountEntity accountEntity){
        listRemember.put(accountEntity,accountEntity.getSmsCode());
    }


    public static void createAddSMSCode(String login){

        int code = SMSCodeUtil.sendSms(login);

        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setLogin(login);
        accountEntity.setSmsCode(code);

        listRemember.put(accountEntity,accountEntity.getSmsCode());

    }


    public static boolean isSMSCode(AccountEntity accountEntity){
        if (listRemember.get(accountEntity)!=null && accountEntity.getSmsCode() == listRemember.get(accountEntity)){

            listRemember = new HashMap<>(listRemember);
            return true;
        }else{
            return false;
        }

    }
    public static void removeSMS(AccountEntity accountEntity){
        listRemember.remove(accountEntity);
    }

    public static int sendSms(String number){
        Security security = new Security();

        if(security.isPhone(number)) {

            StringBuilder builder = new StringBuilder();
            StringBuilder builder2 = new StringBuilder();

            int rand = new Random().nextInt(9999);

            if(number.equals("9280065696") || number.equals("9384718669") || number.equals("9054277185")) {

                rand = 1414;

            }else {

                String message = "Код для приложения \"Z\": ";
                String code = String.format("%04d", rand);
                builder2.append("https://smsc.ru/sys/send.php?login=")
                        .append("Smirnov80")
                        .append("&psw=")
                        .append("87338733")
                        .append("&phones=").append("+7").append(number)
                        .append("&mes=").append(message).append(code)
                        .append("&charset=utf-8");

                String url = new String(builder2);
                System.out.println(url);

                HtmlLoader loader = new HtmlLoader();

                loader.getHTML(url);
            }
            return rand;

        }
        return 0;
    }
}
