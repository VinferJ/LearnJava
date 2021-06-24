package dianchu.atmosphere;

/**
 * @author vinfer
 * @date 2021-03-10 16:45
 */
public interface GenericNotifier {

    void registerObserver(GenericBulletinBoard bulletinBoard);

    void removeObserver(GenericBulletinBoard bulletinBoard);

    void notifyAllObserver();

}
