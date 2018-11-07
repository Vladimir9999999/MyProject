package interfaces;

import Models.EmploeeEntity;

public interface AuthInterface {

    boolean auth(EmploeeEntity user);
    boolean exit(EmploeeEntity user);
    boolean registartion(EmploeeEntity user);
    boolean updatePasword(EmploeeEntity user);
}
