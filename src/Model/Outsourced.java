package Model;

/**
 *
 * @author antonio
 */
public class Outsourced extends Part {

    private String companyName;
    /**
     * @param id id to assign
     * @param name name to assign
     * @param price price to assign
     * @param stock stock to assign
     * @param min min to assign
     * @param max max to assign
     * @param companyName company name to assign
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;  
    }
    
    /**
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    /**
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }
}
