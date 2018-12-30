package insa.clutchgames.wallpass.models;

public interface CollidableShape{
    void initPhysics();
    void setFilter(int type, int mask);
}
