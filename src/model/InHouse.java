package model;

/** InHouse Class
 * @author Isaac Lee
 * Part based Class
 */
public class InHouse extends Part {
    private int machineId;

    /** Inhouse
     * Part constructor
     * Used inheritance from Part Abstract for all but machineId which is unique to the Inhouse class
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }


    /** RUNTIME ERROR
     * @return machineId of the Inhouse Class
     * RUNTIME ERROR - "Cannot find Symbol" error generated during runtime for machineID.
     * RUNTIME ERROR SOLUTION - Resolved by using the 'Invalidate cache & Restart' in Intellij. Solution source => https://stackoverflow.com/questions/12132003/getting-cannot-find-symbol-in-java-project-in-intellij
     */
    public int getMachineId() {
        return machineId;
    }


    /** RUNTIME ERROR
     * @param machineId the machineId to set
     *  RUNTIME ERROR - "Cannot find Symbol" error generated during runtime for machineID.
     *  RUNTIME ERROR SOLUTION - Resolved by using the 'Invalidate cache & Restart' in Intellij. Solution source => https://stackoverflow.com/questions/12132003/getting-cannot-find-symbol-in-java-project-in-intellij
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
