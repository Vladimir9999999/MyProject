package utils;

import Models.QrCode;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

public class QRManager implements Runnable {

    static final   LinkedList<QrCode> qrCodes = new LinkedList<>();
    static QrCode qrCode = new QrCode();

    static {

        qrCode.setHost(0);
        qrCode.setType(QrCode.USER);
        qrCode.setHash("87338733");
        qrCode.setDate(new Date());

        qrCodes.add(qrCode);

    }

    public static LinkedList<QrCode> getListQrCode() {

        return qrCodes;

    }

    private static Thread thr;

    @Override
    public void run() {

       /*  необходимо для удаления кодов по таймауту
       while (false){
            try {

                Thread.sleep(60000);
                update();
                if(qrCodes.size()==0){
                    break;
                }
            } catch (Exception e) {

                e.printStackTrace();

            }

        }*/
    }
    public static QrCode selectForPhone(long phone){

        for (QrCode c: qrCodes){
            if (c.getReserved() ==phone){
                return c;
            }
        }

        return null;
    }
    public static void start(){
        if (thr == null) {

            thr = new Thread(new QRManager());
            thr.start();

        }
    }

    public static void addQr(QrCode code){
        qrCodes.add(code);
    }

    public static QrCode getFromHash(String hash){
        QrCode code = new QrCode();
        code.setHash(hash);
        return getQr(code);
    }

    public static QrCode isQr(QrCode code){
        int i= qrCodes.indexOf(code);
        if(i  != -1){
             return  qrCodes.get(i);
        }
        return null;
    }

    public static QrCode getQr(QrCode code){

        for(QrCode qr: qrCodes){
            if (qr.equals(code)){

                code.setId(qr.getId());
                code.setType(qr.getType());
                code.setHost(qr.getHost());
                if(!code.getHash().equals("87338733")) {

                    qrCodes.remove(qr);

                }else {
                    qr.setReserved(0);
                }
                return qr;

            }
        }
        return null;
    }

    private static void update(){
        int count_dell=0;
        for (QrCode code:qrCodes){
            long time = new Date().getTime()-code.getDate().getTime();
            if(time>=10*1000){
                count_dell++;
                qrCodes.remove(code);
            }
        }
        System.out.print("deleted qr: "+count_dell);
    }

}
