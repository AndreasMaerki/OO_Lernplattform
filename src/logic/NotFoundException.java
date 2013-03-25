package logic;

/** Diese Exception wird geworfen, wenn ein Objekt nicht gefunden wurde
 *
 * @autor Christian Binder
 * @version 1.00, 12 August 2012
 * @author Christian
 */
public class NotFoundException extends Exception {

    private Object mThing;

    /** Erzeugt eine neue Exception
     *
     * @autor Christian Binder
     * @version 1.00, 12 August 2012
     * @param pThing Das nicht gefundene Objekt
     */
    public NotFoundException(Object pThing) {
        super();
        mThing = pThing;
    }

    @Override
    public String getMessage() {
        return mThing + " not found";
    }//Ende getMessage()
}//Ende NotFoundException

