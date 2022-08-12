package model;

/** Outsourced Clas
 * @author Isaac Lee
 * Part based Class
 */
public class Outsourced extends Part {
    private String companyName;

    /** Outsourced
     * Part constructor
     * Used inheritance from Part Abstract for all but companyName which is unique to the Inhouse class
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }


    /** getCompanyName
     * Getter
     * @return companyName of the outsourced  Class
     */
    public String getCompanyName() {
        return companyName;
    }


    /** setCompanyName
     * Setter
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}