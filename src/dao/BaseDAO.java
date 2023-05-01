package dao;

public abstract class BaseDAO {
    protected DBConnection dbConnection;

    public BaseDAO() {
        dbConnection = DBConnection.getInstance();
        prepareStatements();
    }

    protected abstract void prepareStatements();
}
