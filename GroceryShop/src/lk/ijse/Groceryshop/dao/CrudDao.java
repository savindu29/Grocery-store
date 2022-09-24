package lk.ijse.Groceryshop.dao;

public interface CrudDao <T,ID>{
    public boolean save(T t);
    public boolean delete(ID id);
    public boolean update(T t);
}
