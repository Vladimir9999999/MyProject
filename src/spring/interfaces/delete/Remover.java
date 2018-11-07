package spring.interfaces.delete;

public interface Remover {

    boolean save(Removable entity);
    Removable selectById(long id);
}
