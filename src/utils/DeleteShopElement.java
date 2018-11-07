package utils;

import spring.entity.EntityDeleteMarker;
import spring.interfaces.DeleteMarkerDao;
import spring.interfaces.delete.Removable;
import spring.interfaces.delete.Remover;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class DeleteShopElement {


    private long shopId;
    private DeleteMarkerDao deleteMarkerDao;

    public DeleteShopElement(long shopId, DeleteMarkerDao deleteMarkerDao) {
        this.shopId = shopId;
        this.deleteMarkerDao = deleteMarkerDao;
    }

    public void  markToDelete(Removable entity, Remover remover){


        boolean isEmployee = (entity !=null && entity.getShopId()==shopId);

        if(isEmployee){

            GregorianCalendar calendar = new GregorianCalendar();

            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);

            Timestamp date = new Timestamp(calendar.getTimeInMillis());
            System.out.println(date);
            System.out.println(calendar);

            EntityDeleteMarker marker = deleteMarkerDao.selectByDate(date);
            if (marker == null) {

                marker = new EntityDeleteMarker();
                marker.setDate(date);
                marker.setEntityEmployees(new HashSet<>());
            }
            entity.setDeleteMarker(marker);
            deleteMarkerDao.save(marker);
            remover.save(entity);
        }
    }

}
