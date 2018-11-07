package utils;

public class Security {
    public boolean isPhone(String phone){
        long p=0;
        String error = "неверный формат телефонного номера";
        try{
            p = Long.parseLong(phone);

        }catch (NumberFormatException e){
            System.out.println(error);
        }
        if(p<1000000000L){
            System.out.println(error);
        }else{
            return true;
        }
        return false;
    }
    public boolean isInjection(String field){
        return false;
    }
    public boolean isMail(String mail){
        return false;
    }
}
